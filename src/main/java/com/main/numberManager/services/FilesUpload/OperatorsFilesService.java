package com.main.numberManager.services.FilesUpload;

import com.main.numberManager.models.OperatorsModel;
import com.main.numberManager.repositorys.OperatorsRepository;
import com.main.numberManager.services.serviceImpl.FileHandlingImp;
import com.main.numberManager.utils.FileUtils;
import com.main.numberManager.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class OperatorsFilesService implements FileHandlingImp<OperatorsModel> {
    private final OperatorsRepository operatorsRepository;

    public OperatorsFilesService(OperatorsRepository operatorsRepository) {
        this.operatorsRepository = operatorsRepository;
    }

    public void processFile(MultipartFile file) throws IOException {
        try (Stream<String> lines = FileUtils.readerFile(file)) {
            List<OperatorsModel> batch = new ArrayList<>();
            int batchSize = 1000; // Define o tamanho do lote

            lines.forEach(line -> {
                if (!line.isEmpty()) {
                    OperatorsModel model = FileUtils.mapToModel(StringUtils.cleanLineKeepingSeparator(line),
                            OperatorsModel.class);

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

    public void saveBatch(List<OperatorsModel> batch) {
        operatorsRepository.saveAll(batch);
    }

}

