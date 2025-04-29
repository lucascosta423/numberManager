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


    public void createNumberListForPortability(RequestPortabilityModel solicitacao, List<String> numberList) {
        List<NumberForPortabilityModel> list = numberList.stream()
                .map(numero -> createNumberForPortability(numero, solicitacao))
                .toList();

        saveAll(list);
    }

    private void saveAll(List<NumberForPortabilityModel> listaNumeros){
        numberForPortabilityRepository.saveAll(listaNumeros);
    }

    private void save(NumberForPortabilityModel model) {
        try {
            numberForPortabilityRepository.save(model);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar atualização da portabilidade", e);
        }
    }

    public SucessResponse update(String id, UpdateNumberForPortabilityDTO dto){
        NumberForPortabilityModel numberForPortabilityModel = findById(id);

        updateNumberModelFields(numberForPortabilityModel, dto);
        save(numberForPortabilityModel);

        return new SucessResponse("Solicitacao atualizada com sucesso","OK");
    }

    private NumberForPortabilityModel findById(String id){
        return numberForPortabilityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Solicitacao para o Numero nao encontrado"));
    }

    private NumberForPortabilityModel createNumberForPortability(String numero, RequestPortabilityModel solicitation) {

        NumberForPortabilityModel numberForPortability = new NumberForPortabilityModel();

        BeanUtils.copyProperties(getDadosOperadora(numero), numberForPortability, "id", "solicitacaoPortabilidadeModel", "numero");

        fillDataNumber(numberForPortability, numero, solicitation);

        return numberForPortability;
    }

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
