package com.main.numberManager.services;

import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.NumeroModel;
import com.main.numberManager.repositorys.NumeroRepository;
import com.main.numberManager.services.serviceImpl.FileHandlingImp;
import com.main.numberManager.utils.CnlUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class NumeroService implements FileHandlingImp<NumeroModel> {
    private final NumeroRepository numeroRepository;

    public NumeroService(NumeroRepository numeroRepository) {
        this.numeroRepository = numeroRepository;
    }


    public NumeroModel save(NumeroModel numeroModel) {
        return numeroRepository.save(numeroModel);
    }

    public NumeroModel findById(Integer id) {

        return numeroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Numero não encontrado"));
    }

    public Page<NumeroModel> findAll(Pageable pageable) {
        return numeroRepository.findAll(pageable);
    }

    @Override
    public void processFile(MultipartFile file) throws IOException {
        try (Stream<String> lines = CnlUtils.readerFile(file)) {
            List<NumeroModel> batch = new ArrayList<>();
            int batchSize = 1000;

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
}
