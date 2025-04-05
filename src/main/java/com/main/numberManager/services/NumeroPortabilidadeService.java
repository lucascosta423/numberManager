package com.main.numberManager.services;

import com.main.numberManager.models.NumeroPortabilidadeModel;
import com.main.numberManager.repositorys.NumeroPortabilidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NumeroPortabilidadeService {
    private final NumeroPortabilidadeRepository numeroPortabilidadeRepository;
    private final OperadorasService operadorasService;

    public NumeroPortabilidadeService(NumeroPortabilidadeRepository numeroPortabilidadeRepository, OperadorasService operadorasService) {
        this.numeroPortabilidadeRepository = numeroPortabilidadeRepository;
        this.operadorasService = operadorasService;
    }
    public void save(List<NumeroPortabilidadeModel> numeroPortabilidadeModel){
        numeroPortabilidadeRepository.saveAll(numeroPortabilidadeModel);
    }
}
