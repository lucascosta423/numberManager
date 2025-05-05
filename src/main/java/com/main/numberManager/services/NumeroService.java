package com.main.numberManager.services;

import com.main.numberManager.Enuns.Status;
import com.main.numberManager.dtos.Number.RequestNumberUpdateDTO;
import com.main.numberManager.dtos.Number.RequestReserveNumberDTO;
import com.main.numberManager.dtos.Number.ResponseAllNumbersDto;
import com.main.numberManager.exeptions.BusinessException;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.NumeroModel;
import com.main.numberManager.models.ProviderModel;
import com.main.numberManager.repositorys.NumeroRepository;
import com.main.numberManager.utils.AuthUtils;
import com.main.numberManager.utils.responseApi.SucessResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NumeroService{
    private final NumeroRepository numeroRepository;
    private final ProviderService providerService;
    private final AuthUtils authUtils;

    public NumeroService(NumeroRepository numeroRepository, ProviderService providerService, AuthUtils authUtils) {
        this.numeroRepository = numeroRepository;
        this.providerService = providerService;
        this.authUtils = authUtils;
    }

    public SucessResponse requestNumberActivate(Integer id, RequestNumberUpdateDTO dto){

        NumeroModel numeroModel = findById(id);
        BeanUtils.copyProperties(dto,numeroModel,"id","cn","mcdu","area","provedor","status" );

        numeroModel.setStatus(Status.P);
        numeroModel.setDataSolicitacao(LocalDateTime.now());

        numeroRepository.save(numeroModel);

        return new SucessResponse("Solicitacao de ativacao criada com sucesso","OK");
    }

    public SucessResponse activateNumber(Integer id){

        NumeroModel numeroModel = findById(id);

        numeroModel.setStatus(Status.A);
        numeroModel.setDataAtivacao(LocalDateTime.now());

        numeroRepository.save(numeroModel);

        return new SucessResponse("Numero ativado","OK");
    }

    public SucessResponse reserveNumber(RequestReserveNumberDTO dto){

        ProviderModel providerModel = providerService.findById(dto.provedor());

        if(providerModel.verifyStatus()){
            throw new BusinessException("Provedor inativo");
        }

        List<NumeroModel> numeros = numeroRepository.findAllById(dto.idsNumeros());

        if(numeros.size() != dto.idsNumeros().size()){
            throw new BusinessException("Um ou mais números não foram encontrados");
        }

        for (NumeroModel numero : numeros) {

            if (!numero.getStatus().equals(Status.N)) {
                throw new BusinessException("Número " + numero.getNumero() + " não está disponível para reserva");
            }
            numero.setStatus(Status.R);
            numero.setDataResevada(LocalDateTime.now());
            numero.setProvedor(providerModel);
        }
        numeroRepository.saveAll(numeros);

        return new SucessResponse("Reserva de numeros realizada com sucesso","OK");
    }

    public NumeroModel findById(Integer id) {
        return numeroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Numero não encontrado"));
    }

    public Page<ResponseAllNumbersDto> findAll(Pageable pageable) {

        if (authUtils.isAdmin()) {
            return numeroRepository.findAll(pageable)
                    .map(ResponseAllNumbersDto::fromEntity);
        }else {
            return numeroRepository.findByProvedor(authUtils.getCurrentUser().getProvedor(),pageable)
                    .map(ResponseAllNumbersDto::fromEntity);
        }

    }

    private Status parseStatus(String statusStr) {
        try {
            return Status.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Status inválido: " + statusStr);
        }
    }

}
