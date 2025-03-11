package com.main.numberManager.services.numero;

import com.main.numberManager.repositorys.numeros.NumeroRepository;
import org.springframework.stereotype.Service;

@Service
public class NumeroService {
    private final NumeroRepository numeroRepository;

    public NumeroService(NumeroRepository numeroRepository) {
        this.numeroRepository = numeroRepository;
    }


}
