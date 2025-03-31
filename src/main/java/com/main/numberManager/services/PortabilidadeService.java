package com.main.numberManager.services;


import com.main.numberManager.models.PortabilidadeModel;
import com.main.numberManager.repositorys.PortabilidadeRepository;
import org.springframework.stereotype.Service;

@Service
public class PortabilidadeService {

    private final PortabilidadeRepository portabilidadeRepository;

    public PortabilidadeService(PortabilidadeRepository portabilidadeRepository) {
        this.portabilidadeRepository = portabilidadeRepository;
    }


    public PortabilidadeModel save(PortabilidadeModel portabilidadeModel) {
        return portabilidadeRepository.save(portabilidadeModel);
    }
}
