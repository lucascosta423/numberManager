package com.main.numberManager.services;

import com.main.numberManager.Enuns.Status;
import com.main.numberManager.dtos.Number.RequestNumberUpdateDTO;
import com.main.numberManager.dtos.Number.RequestReserveNumberDTO;
import com.main.numberManager.dtos.Number.ResponseAllNumbersDto;
import com.main.numberManager.exeptions.BusinessException;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.NumeroModel;
import com.main.numberManager.models.ProviderModel;
import com.main.numberManager.models.UsuarioModel;
import com.main.numberManager.repositorys.NumeroRepository;
import com.main.numberManager.utils.AuthenticatedUser;
import com.main.numberManager.utils.responseApi.SucessResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NumeroService{
    private final NumeroRepository numeroRepository;
    private final ProviderService providerService;

    public NumeroService(NumeroRepository numeroRepository, ProviderService providerService) {
        this.numeroRepository = numeroRepository;
        this.providerService = providerService;
    }

    public static UsuarioModel AuthenticatedUser() {
        return (UsuarioModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public SucessResponse activateNumber(Integer id, RequestNumberUpdateDTO dto){

        NumeroModel numeroModel = findById(id);
        BeanUtils.copyProperties(dto,numeroModel,"id","cn","mcdu","area","provedor");

        numeroModel.setStatus(parseStatus(dto.status()));

        return new SucessResponse("Numero ativado com sucesso","OK");
    }

    public SucessResponse reserveNumber(RequestReserveNumberDTO dto){

        ProviderModel providerModel = providerService.findById(dto.provedor());

        if(providerModel.getStatus().equals(Status.I)){
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
        var usuario = AuthenticatedUser();

        if (isAdmin()) {
            return numeroRepository.findAll(pageable)
                    .map(ResponseAllNumbersDto::fromEntity);
        }else {
            return numeroRepository.findByProvedor(usuario.getProvedor(),pageable)
                    .map(ResponseAllNumbersDto::fromEntity);
        }

    }

    private boolean isAdmin() {
        var usuario = AuthenticatedUser();

        return usuario.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }

    private Status parseStatus(String statusStr) {
        try {
            return Status.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Status inválido: " + statusStr);
        }
    }

}
