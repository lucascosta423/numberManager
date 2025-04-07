package com.main.numberManager.services;

import com.main.numberManager.models.SolicitacaoNumeroModel;
import com.main.numberManager.models.SolicitacaoPortabilidadeModel;
import com.main.numberManager.repositorys.SolicitacaoNumeroRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitacaoNumeroService {
    private final SolicitacaoNumeroRepository solicitacaoNumeroRepository;
    private final OperadorasService operadorasService;

    public SolicitacaoNumeroService(SolicitacaoNumeroRepository solicitacaoNumeroRepository, OperadorasService operadorasService) {
        this.solicitacaoNumeroRepository = solicitacaoNumeroRepository;
        this.operadorasService = operadorasService;
    }


    private void save(List<SolicitacaoNumeroModel> listaNumeros){
        solicitacaoNumeroRepository.saveAll(listaNumeros);
    }


    public void criarNumerosPortabilidade(SolicitacaoPortabilidadeModel solicitacao, List<String> numeros) {
        List<SolicitacaoNumeroModel> list = numeros.stream()
                .map(numero -> criarNumeroPortabilidade(numero, solicitacao))
                .toList();

        save(list);
    }

    private SolicitacaoNumeroModel criarNumeroPortabilidade(String numero, SolicitacaoPortabilidadeModel solicitacao) {

        String codigoNacional = numero.substring(0, 2);
        String prefixo = numero.substring(2, 6);
        String mcdu = numero.substring(6);

        SolicitacaoNumeroModel numeroPortado = new SolicitacaoNumeroModel();
        var dadosOperadora = operadorasService.findByNumeroPortabilidade(prefixo, mcdu, codigoNacional);

        BeanUtils.copyProperties(dadosOperadora, numeroPortado, "id", "solicitacaoPortabilidadeModel", "numero");

        numeroPortado.setNumero(numero);
        numeroPortado.setSolicitacaoPortabilidadeModel(solicitacao);

        return numeroPortado;
    }

}
