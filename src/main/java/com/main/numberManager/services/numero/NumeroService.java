package com.main.numberManager.services.numero;

import com.main.numberManager.models.cnl.CnlGeralModel;
import com.main.numberManager.models.numero.NumeroModel;
import com.main.numberManager.models.provedor.ProvedorModel;
import com.main.numberManager.repositorys.numeros.NumeroRepository;
import com.main.numberManager.repositorys.provedor.ProvedorRepository;
import com.main.numberManager.services.interfaceImpl.InterfaceImpl;
import com.main.numberManager.services.provedor.ProvedorService;
import com.main.numberManager.utils.CnlUtils;
import com.main.numberManager.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class NumeroService implements InterfaceImpl<NumeroModel> {
    private final NumeroRepository numeroRepository;
    private final ProvedorService provedorService;

    public NumeroService(NumeroRepository numeroRepository, ProvedorService provedorService) {
        this.numeroRepository = numeroRepository;
        this.provedorService = provedorService;
    }


    @Override
    public void processFile(MultipartFile file) throws IOException {
        try (Stream<String> lines = CnlUtils.readerFile(file)) {
            List<NumeroModel> batch = new ArrayList<>();
            int batchSize = 1000; // Define o tamanho do lote


            lines.forEach(line -> {

                if (!line.isEmpty()) {
                    NumeroModel model = CnlUtils.mapToModel(line,NumeroModel.class);
                    batch.add(model);

                    if (batch.size() >= batchSize) {
                        saveBatch(batch);
                        batch.clear();
                    }
                }
            });

            if (!batch.isEmpty()) {
                saveBatch(batch);
            }
        }
    }

    @Override
    public void saveBatch(List<NumeroModel> batch) {
        numeroRepository.saveAll(batch);
    }

    public Optional<NumeroModel> findById(Integer id) {
        return numeroRepository.findById(id);
    }

    public NumeroModel save(NumeroModel numeroModel) {
        return numeroRepository.save(numeroModel);
    }

    public Page<NumeroModel> findAll(Pageable pageable) {
        return numeroRepository.findAll(pageable);
    }
}
