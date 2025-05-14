package com.main.numberManager.services;


import com.main.numberManager.Enuns.Status;
import com.main.numberManager.dtos.Portability.RequestPortabilityDTO;
import com.main.numberManager.dtos.Portability.UpdateDocumentOrReason;
import com.main.numberManager.dtos.Portability.ResponsePortabilityDTO;
import com.main.numberManager.dtos.Portability.UpdateNumberForPortabilityDTO;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.RequestPortabilityModel;
import com.main.numberManager.repositorys.RequestPortabilityRepository;
import com.main.numberManager.services.FilesUpload.GoogleCloudStorageService;
import com.main.numberManager.utils.AuthUtils;
import com.main.numberManager.utils.responseApi.SucessResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequestPortabilityService {

    private final RequestPortabilityRepository requestPortabilityRepository;
    private final NumberForPortabilityService requestNumberService;
    private final GoogleCloudStorageService cloudStorageService;
    private final AuthUtils authUtils;

    public RequestPortabilityService(RequestPortabilityRepository requestPortabilityRepository, NumberForPortabilityService requestNumberService, GoogleCloudStorageService cloudStorageService, AuthUtils authUtils) {
        this.requestPortabilityRepository = requestPortabilityRepository;
        this.requestNumberService = requestNumberService;
        this.cloudStorageService = cloudStorageService;
        this.authUtils = authUtils;
    }

    @Transactional
    public SucessResponse save(RequestPortabilityDTO dto) throws IOException {

        String fileUrl = cloudStorageService.uploadFile(dto.getFatura());

        var portabilityModel = new RequestPortabilityModel();
        BeanUtils.copyProperties(dto,portabilityModel,"fileFatura");

        portabilityModel.setFileFatura(fileUrl);
        fillDataPortability(portabilityModel);

        var returnPortabilitySaved = requestPortabilityRepository.save(portabilityModel);

        requestNumberService.createNumberListForPortability(returnPortabilitySaved,dto.getNumeros());

        return new SucessResponse("Solicitaçao criada com sucesso","OK");
    }

    private void fillDataPortability(RequestPortabilityModel portabilityModel){
        portabilityModel.setId(gerarId());
        portabilityModel.setStatus(Status.N);
        portabilityModel.setUsuario(authUtils.getCurrentUser());
        portabilityModel.setProvedor(authUtils.getCurrentUser().getProvedor());
    }

    public SucessResponse updateDocumentOrReason(String id, UpdateDocumentOrReason dto){
        RequestPortabilityModel portabilityModel = findById(id);

        updateDocumentOrReasonFields(portabilityModel, dto);

        requestPortabilityRepository.save(portabilityModel);

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
    public SucessResponse finalizeRequestPortability(String id){
        RequestPortabilityModel portabilidadeModel = findById(id);
        portabilidadeModel.setStatus(Status.F);
        portabilidadeModel.setDataFinalizado(LocalDateTime.now());
        requestPortabilityRepository.save(portabilidadeModel);
        return new SucessResponse("Solicitacao finalizada com sucesso","OK");
    }

    public RequestPortabilityModel findById(String id) {
        return requestPortabilityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitacao nao encontrada"));
    }

    public Page<ResponsePortabilityDTO> findAll(Pageable pageable) {

        if (authUtils.isAdmin()) {
            return requestPortabilityRepository.findAll(pageable)
                    .map(ResponsePortabilityDTO::fromEntity);
        }else {
            return requestPortabilityRepository.findByProvedor(authUtils.getCurrentUser().getProvedor(),pageable)
                    .map(ResponsePortabilityDTO::fromEntity);
        }
    }

    private String gerarId() {
        String prefixo = "SPOR";
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5).toUpperCase();
        return prefixo + uuid;
    }

    public SucessResponse updateNumber(String id, UpdateNumberForPortabilityDTO dto) {
        return requestNumberService.update(id, dto);
    }
}
