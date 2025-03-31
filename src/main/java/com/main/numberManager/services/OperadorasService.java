package com.main.numberManager.services;

import com.main.numberManager.dtos.operadoras.ResponseOperadorasDto;
import com.main.numberManager.models.OperadorasModel;
import com.main.numberManager.repositorys.OperadorasRepository;
import com.main.numberManager.services.serviceImpl.FileHandlingImp;
import com.main.numberManager.utils.CnlUtils;
import com.main.numberManager.utils.StringUtils;
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
public class OperadorasService implements FileHandlingImp<OperadorasModel> {
    private final OperadorasRepository operadorasRepository;

    public OperadorasService(OperadorasRepository operadorasRepository) {
        this.operadorasRepository = operadorasRepository;
    }


    public Page<ResponseOperadorasDto> findAll(Pageable pageable) {
        return operadorasRepository.findAll(pageable)
                .map(ResponseOperadorasDto::fromEntity);
    }


    public Optional<Integer> findByCodigoCnl(Integer prefixo, Integer valor, Integer codigoNacional){
        return operadorasRepository.findByCodigoCnl(prefixo,valor,codigoNacional);
    }

    public void processFile(MultipartFile file) throws IOException {
        try (Stream<String> lines = CnlUtils.readerFile(file)) {
            List<OperadorasModel> batch = new ArrayList<>();
            int batchSize = 1000; // Define o tamanho do lote

            lines.forEach(line -> {
                if (!line.isEmpty()) {
                    OperadorasModel model = CnlUtils.mapToModel(StringUtils.cleanLineKeepingSeparator(line),
                            OperadorasModel.class);

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

    public void saveBatch(List<OperadorasModel> batch) {
        operadorasRepository.saveAll(batch);
    }

}

