package com.main.numberManager.services;


import com.main.numberManager.Enuns.Status;
import com.main.numberManager.dtos.Portability.ResponsePortabilityDTO;
import com.main.numberManager.dtos.Portability.RequestPortabilityDTO;
import com.main.numberManager.models.RequestPortabilityModel;
import com.main.numberManager.models.UsuarioModel;
import com.main.numberManager.repositorys.RequestPortabilityRepository;
import com.main.numberManager.utils.responseApi.SucessResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class RequestPortabilityService {

    private final RequestPortabilityRepository requestPortabilityRepository;
    private final NumberForPortabilityService requestNumberService;

    public RequestPortabilityService(RequestPortabilityRepository requestPortabilityRepository, NumberForPortabilityService requestNumberService) {
        this.requestPortabilityRepository = requestPortabilityRepository;
        this.requestNumberService = requestNumberService;
    }

    private UsuarioModel getUsuarioAtual() {
        return (UsuarioModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Transactional
    public SucessResponse save(RequestPortabilityDTO portabilidadeDTO) {
        var usuario = getUsuarioAtual();

        var portabilidadeModel = new RequestPortabilityModel();
        BeanUtils.copyProperties(portabilidadeDTO,portabilidadeModel);

        portabilidadeModel.setStatus(Status.N);
        portabilidadeModel.setUsuario(usuario);
        portabilidadeModel.setProvedor(usuario.getProvedor());
        portabilidadeModel.setId(gerarId());

        var portabilidadeSalva = requestPortabilityRepository.save(portabilidadeModel);

        requestNumberService.createNumberListForPortability(portabilidadeSalva,portabilidadeDTO.numeros());

        return new SucessResponse("Solicitaçao criada com sucesso","OK");
    }

    private void fillDataPortability(RequestPortabilityModel portabilityModel){
        portabilityModel.setId(gerarId());
        portabilityModel.setStatus(Status.N);
        portabilityModel.setUsuario(getUsuarioAtual());
        portabilityModel.setProvedor(getUsuarioAtual().getProvedor());
    }

    public SucessResponse updateDocumentOrReason(String id, UpdateDocumentOrReason dto){
        RequestPortabilityModel portabilidadeModel = findById(id);

        updateDocumentOrReasonFields(portabilidadeModel, dto);

        requestPortabilityRepository.save(portabilidadeModel);

        return new SucessResponse("Solicitacao atualizada com sucesso","OK");
    }

    private void updateDocumentOrReasonFields(RequestPortabilityModel portabilidadeModel, UpdateDocumentOrReason dto){
        Optional.ofNullable(dto.razao())
                .filter(razao -> !razao.trim().isEmpty())
                .ifPresent(portabilidadeModel::setRazao);

        Optional.ofNullable(dto.documento())
                .filter(documento -> !documento.trim().isEmpty())
                .ifPresent(portabilidadeModel::setDocumento);
    }

    @Transactional
    public SucessResponse updateStatus(String id){
        RequestPortabilityModel portabilidadeModel = findById(id);
        portabilidadeModel.setStatus(Status.F);
        requestPortabilityRepository.save(portabilidadeModel);
        return new SucessResponse("Solicitacao finalizada com sucesso","OK");
    }

    public RequestPortabilityModel findById(String id) {
        return requestPortabilityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitacao nao encontrada"));
    }

    public Page<ResponsePortabilityDTO> findAll(Pageable pageable) {
        return requestPortabilityRepository.findAll(pageable)
                .map(ResponsePortabilityDTO::fromEntity);
    }

    private String gerarId() {
        String prefixo = "SPOR";
        int numero = new Random().nextInt(90000) + 10000;
        return prefixo + numero;
    }

    public SucessResponse updateNumber(String id, UpdateNumberForPortabilityDTO dto) {
        return requestNumberService.update(id, dto);
    }
}
