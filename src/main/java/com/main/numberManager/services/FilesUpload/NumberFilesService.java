package com.main.numberManager.services.FilesUpload;

import com.main.numberManager.Enuns.Status;
import com.main.numberManager.domain.baseDids.DidsModel;
import com.main.numberManager.domain.baseDids.DidsRepository;
import com.main.numberManager.domain.providers.ProviderService;
import com.main.numberManager.services.serviceImpl.FileHandlingImp;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.main.numberManager.utils.FileUtils.mapLineToModel;

@Service
public class NumberFilesService implements FileHandlingImp<DidsModel> {

    private final DidsRepository didsRepository;
    private final ProviderService providerService;

    public NumberFilesService(DidsRepository didsRepository, ProviderService providerService) {
        this.didsRepository = didsRepository;
        this.providerService = providerService;
    }

    @Override
    public void processFile(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.ISO_8859_1))) {
            String headerLine = reader.readLine();
            String[] headers = headerLine.split(";");

            List<DidsModel> batch = new ArrayList<>();
            int batchSize = 1000;

            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {

                    DidsModel numero = mapLineToModel(line, headers, DidsModel::new, (model, header, value) -> {
                        switch (header) {
                            case "cn" -> model.setCn(value);
                            case "prefixo" -> model.setPrefixo(value);
                            case "mcdu" -> model.setMcdu(value);
                            case "area" -> model.setArea(value);
                            case "cliente" -> model.setCliente(value);
                            case "documento" -> model.setDocumento(value);
                            case "provedor" -> {
                                Integer idProvedor = Integer.parseInt(value);
                                model.setProvedor(providerService.findById(idProvedor));
                            }
                            case "status" -> model.setStatus(Status.valueOf(value));
                        }
                        if (model.getStatus() == null) {
                            model.setStatus(Status.N);
                        }
                    });
                    batch.add(numero);

                    if (batch.size() >= batchSize) {
                        saveBatch(batch);
                        batch.clear();
                    }
                }
            }

            if (!batch.isEmpty()) {
                saveBatch(batch);
            }
        }
    }

    @Override
    public void saveBatch(List<DidsModel> batch) {
        didsRepository.saveAll(batch);
    }
}
