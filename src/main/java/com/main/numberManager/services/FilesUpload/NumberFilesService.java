package com.main.numberManager.services.FilesUpload;

import com.main.numberManager.Enuns.Status;
import com.main.numberManager.models.NumeroModel;
import com.main.numberManager.repositorys.NumeroRepository;
import com.main.numberManager.services.ProviderService;
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
public class NumberFilesService implements FileHandlingImp<NumeroModel> {

    private final NumeroRepository numeroRepository;
    private final ProviderService providerService;

    public NumberFilesService(NumeroRepository numeroRepository, ProviderService providerService) {
        this.numeroRepository = numeroRepository;
        this.providerService = providerService;
    }

    @Override
    public void processFile(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.ISO_8859_1))) {
            String headerLine = reader.readLine();
            String[] headers = headerLine.split(";");

            List<NumeroModel> batch = new ArrayList<>();
            int batchSize = 1000;

            String line;

            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {

                    NumeroModel numero = mapLineToModel(line, headers, NumeroModel::new, (model, header, value) -> {
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
    public void saveBatch(List<NumeroModel> batch) {
        numeroRepository.saveAll(batch);
    }
}
