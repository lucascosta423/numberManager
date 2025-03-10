package com.main.numberManager.services.provedor;

import com.main.numberManager.models.provedor.ProvedorModel;
import com.main.numberManager.repositorys.provedor.ProvedorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProvedorService {

    private final ProvedorRepository provedorRepository;

    public ProvedorService(ProvedorRepository provedorRepository) {
        this.provedorRepository = provedorRepository;
    }

    public ProvedorModel save(ProvedorModel model) {
        return provedorRepository.save(model);
    }

    public Page<ProvedorModel> findAll(Pageable pageable) {
        return provedorRepository.findAll(pageable);
    }

    public Optional<ProvedorModel> findById(Integer id) {
        return provedorRepository.findById(id);
    }
}
