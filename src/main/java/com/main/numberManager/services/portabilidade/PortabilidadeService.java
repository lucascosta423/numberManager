package com.main.numberManager.services.portabilidade;


import com.main.numberManager.models.portabilidade.PortabilidadeModel;
import com.main.numberManager.repositorys.solicitacaoPortabilidade.PortabilidadeRepository;
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
