package com.main.numberManager.services;

import com.main.numberManager.Enuns.Status;
import com.main.numberManager.dtos.numero.RequestNumeroUpdateDTO;
import com.main.numberManager.dtos.numero.ResponseFindAllNumerosDto;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.NumeroModel;
import com.main.numberManager.repositorys.NumeroRepository;
import com.main.numberManager.services.serviceImpl.FileHandlingImp;
import com.main.numberManager.utils.responseApi.SucessResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class NumeroService implements FileHandlingImp<NumeroModel> {
    private final NumeroRepository numeroRepository;
    private final ProvedorService provedorService;

    public NumeroService(NumeroRepository numeroRepository, ProvedorService provedorService) {
        this.numeroRepository = numeroRepository;
        this.provedorService = provedorService;
    }

    public SucessResponse saveNumero(Integer id, RequestNumeroUpdateDTO dto){

        NumeroModel numeroModel = findById(id);
        BeanUtils.copyProperties(dto,numeroModel,"id","cn","mcdu","area");

        numeroModel.setProvedor(provedorService.findById(dto.idProvedor()));

        numeroModel.setStatus(Status.valueOf(dto.status()));

        return new SucessResponse("Numero atualizado com sucesso","OK");
    }

    public NumeroModel findById(Integer id) {
        return numeroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Numero não encontrado"));
    }

    public Page<ResponseFindAllNumerosDto> findAll(Pageable pageable) {
        return numeroRepository.findAll(pageable)
                .map(ResponseFindAllNumerosDto::fromEntity);
    }

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
                                model.setProvedor(provedorService.findById(idProvedor));
                            }
                            case "status" -> model.setStatus(Status.valueOf(value));
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
