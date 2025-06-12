package com.main.numberManager.domain.baseDids;

import com.main.numberManager.Enuns.Status;
import com.main.numberManager.domain.providers.ProviderService;
import com.main.numberManager.dtos.Number.RequestNumberUpdateDTO;
import com.main.numberManager.dtos.Number.RequestReserveNumberDTO;
import com.main.numberManager.dtos.Number.ResponseAllNumbersDto;
import com.main.numberManager.exeptions.BusinessException;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.domain.providers.ProviderModel;
import com.main.numberManager.utils.AuthUtils;
import com.main.numberManager.utils.responseApi.SucessResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DidsService {
    private final DidsRepository didsRepository;
    private final ProviderService providerService;
    private final AuthUtils authUtils;

    public DidsService(DidsRepository didsRepository, ProviderService providerService, AuthUtils authUtils) {
        this.didsRepository = didsRepository;
        this.providerService = providerService;
        this.authUtils = authUtils;
    }

    public SucessResponse requestNumberActivate(Integer id, RequestNumberUpdateDTO dto){

        DidsModel didsModel = findById(id);
        BeanUtils.copyProperties(dto, didsModel,"id","cn","mcdu","area","provedor","status" );

        didsModel.setStatus(Status.P);
        didsModel.setDataSolicitacao(LocalDateTime.now());

        didsRepository.save(didsModel);

        return new SucessResponse("Solicitacao de ativacao criada com sucesso","OK");
    }

    public SucessResponse activateNumber(Integer id){

        DidsModel didsModel = findById(id);

        didsModel.setStatus(Status.A);
        didsModel.setDataAtivacao(LocalDateTime.now());

        didsRepository.save(didsModel);

        return new SucessResponse("Numero ativado","OK");
    }

    public SucessResponse reserveNumber(RequestReserveNumberDTO dto){

        ProviderModel providerModel = providerService.findById(dto.provedor());

        if(providerModel.verifyStatus()){
            throw new BusinessException("Provedor inativo");
        }

        List<DidsModel> numeros = didsRepository.findAllById(dto.idsNumeros());

        if(numeros.size() != dto.idsNumeros().size()){
            throw new BusinessException("Um ou mais números não foram encontrados");
        }

        for (DidsModel numero : numeros) {

            if (!numero.getStatus().equals(Status.N)) {
                throw new BusinessException("Número " + numero.getNumero() + " não está disponível para reserva");
            }
            numero.setStatus(Status.R);
            numero.setDataResevada(LocalDateTime.now());
            numero.setProvedor(providerModel);
        }
        didsRepository.saveAll(numeros);

        return new SucessResponse("Reserva de numeros realizada com sucesso","OK");
    }

    public DidsModel findById(Integer id) {
        return didsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Numero não encontrado"));
    }

    public Page<ResponseAllNumbersDto> findAll(Pageable pageable) {

        if (authUtils.isAdmin()) {
            return didsRepository.findAll(pageable)
                    .map(ResponseAllNumbersDto::fromEntity);
        }else {
            return didsRepository.findByProvedor(authUtils.getCurrentUser().getProvedor(),pageable)
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
