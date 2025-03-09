package com.main.numberManager.services.cnl;

import com.main.numberManager.models.basesCnl.CnlGeralModel;
import com.main.numberManager.repositorys.cnl.CnlGeralRepository;
import com.main.numberManager.utils.CnlUtils;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CnlGeralService implements CnlInterface{
    private final CnlGeralRepository cnlGeralRepository;

    public CnlGeralService(CnlGeralRepository cnlGeralRepository) {
        this.cnlGeralRepository = cnlGeralRepository;
    }

    @Async
    public void processFile(MultipartFile file) throws IOException {
        try (Stream<String> lines = CnlUtils.readerFile(file)) {
            List<CnlGeralModel> batch = new ArrayList<>();
            int batchSize = 1000; // Define o tamanho do lote

            lines.forEach(line -> {
                if (!line.isEmpty()) {
                    CnlGeralModel model = CnlUtils.mapToModel(line, CnlGeralModel.class);
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

    @Transactional
    public void saveBatch(List<CnlGeralModel> batch) {
        cnlGeralRepository.saveAll(batch);
    }



}

