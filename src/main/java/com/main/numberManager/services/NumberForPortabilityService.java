package com.main.numberManager.services;

import com.main.numberManager.Enuns.StatusPortability;
import com.main.numberManager.dtos.Portability.UpdateNumberForPortabilityDTO;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.NumberForPortabilityModel;
import com.main.numberManager.models.OperatorsModel;
import com.main.numberManager.models.RequestPortabilityModel;
import com.main.numberManager.repositorys.NumberForPortabilityRepository;
import com.main.numberManager.utils.responseApi.SucessResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NumberForPortabilityService {
    private final NumberForPortabilityRepository numberForPortabilityRepository;
    private final OperatorsService operatorsService;

    public NumberForPortabilityService(NumberForPortabilityRepository numberForPortabilityRepository, OperatorsService operatorsService) {
        this.numberForPortabilityRepository = numberForPortabilityRepository;
        this.operatorsService = operatorsService;
    }


    private void save(List<NumberForPortabilityModel> listaNumeros){
        numberForPortabilityRepository.saveAll(listaNumeros);
    }


    public void createNumberListForPortability(RequestPortabilityModel solicitacao, List<String> numeros) {
        List<NumberForPortabilityModel> list = numeros.stream()
                .map(numero -> createNumberForPortability(numero, solicitacao))
                .toList();

        save(list);
    }

    private NumberForPortabilityModel createNumberForPortability(String numero, RequestPortabilityModel solicitacao) {

        String codigoNacional = numero.substring(0, 2);
        String prefixo = numero.substring(2, 6);
        String mcdu = numero.substring(6);

        NumberForPortabilityModel numeroPortado = new NumberForPortabilityModel();
        var dadosOperadora = operatorsService.findByNumeroPortabilidade(prefixo, mcdu, codigoNacional);

        BeanUtils.copyProperties(dadosOperadora, numeroPortado, "id", "solicitacaoPortabilidadeModel", "numero");

    private void fillDataNumber(NumberForPortabilityModel numberForPortability, String numberToFill, RequestPortabilityModel solicitation) {
        numberForPortability.setId(gerarId());
        numberForPortability.setStatusSolicitacao(StatusPortability.P);
        numberForPortability.setNumero(numberToFill);
        numberForPortability.setRequestPortabilityModel(solicitation);
    }

    private OperatorsModel getDadosOperadora(String numero) {
        return operatorsService.findByNumeroPortabilidade(
                numero.substring(0, 2),
                numero.substring(2, 6),
                numero.substring(6)
        );
    }

    private String gerarId() {
        String prefixo = "SNUM";
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5).toUpperCase();
        return prefixo + uuid;
    }

    private void updateNumberModelFields(NumberForPortabilityModel model, UpdateNumberForPortabilityDTO dto){
        Optional.ofNullable(dto.status())
                .filter(status -> !status.trim().isEmpty())
                .ifPresent(status -> updateStatus(model,status));

        Optional.ofNullable(dto.dataAgendamento())
                .filter(data -> !data.trim().isEmpty())
                .ifPresent(model::setDataAgendamento);

        Optional.ofNullable(dto.horaAgendamento())
                .filter(hora -> !hora.trim().isEmpty())
                .ifPresent(model::setHoraAgendamento);
    }

    private void updateStatus(NumberForPortabilityModel model, String status) {
        try {
            StatusPortability statusEnum = StatusPortability.valueOf(status.trim().toUpperCase());
            model.setStatusSolicitacao(statusEnum);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status de portabilidade inválido: " + status);
        }
    }


}
