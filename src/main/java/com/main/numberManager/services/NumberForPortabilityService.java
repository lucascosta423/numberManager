package com.main.numberManager.services;

import com.main.numberManager.models.NumberForPortabilityModel;
import com.main.numberManager.models.RequestPortabilityModel;
import com.main.numberManager.repositorys.NumberForPortabilityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NumberForPortabilityService {
    private final NumberForPortabilityRepository numberForPortabilityRepository;
    private final OperadorasService operadorasService;

    public NumberForPortabilityService(NumberForPortabilityRepository numberForPortabilityRepository, OperadorasService operadorasService) {
        this.numberForPortabilityRepository = numberForPortabilityRepository;
        this.operadorasService = operadorasService;
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
        var dadosOperadora = operadorasService.findByNumeroPortabilidade(prefixo, mcdu, codigoNacional);

        BeanUtils.copyProperties(dadosOperadora, numeroPortado, "id", "solicitacaoPortabilidadeModel", "numero");

        numeroPortado.setNumero(numero);
        numeroPortado.setRequestPortabilityModel(solicitacao);

        return numeroPortado;
    }

}
