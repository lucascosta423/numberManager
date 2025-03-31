package com.main.numberManager.services.operadoras;

import com.main.numberManager.models.operadoras.Operadoras;
import com.main.numberManager.repositorys.operadoras.OperadorasRepository;
import com.main.numberManager.services.serviceImpl.IService;
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
public class OperadorasService implements IService<Operadoras> {
    private final OperadorasRepository operadorasRepository;

    public OperadorasService(OperadorasRepository operadorasRepository) {
        this.operadorasRepository = operadorasRepository;
    }


    public Page<Operadoras> findAll(Pageable pageable) {
        return operadorasRepository.findAll(pageable);
    }


    public Optional<Operadoras> findNumero(Integer prefixo, Integer valor, Integer codigoNacional){
        return operadorasRepository.findNumero(prefixo,valor,codigoNacional);
    }

    public void processFile(MultipartFile file) throws IOException {
        try (Stream<String> lines = CnlUtils.readerFile(file)) {
            List<Operadoras> batch = new ArrayList<>();
            int batchSize = 1000; // Define o tamanho do lote

            lines.forEach(line -> {
                if (!line.isEmpty()) {
                    Operadoras model = CnlUtils.mapToModel(StringUtils.cleanLineKeepingSeparator(line),
                            Operadoras.class);

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

    public void saveBatch(List<Operadoras> batch) {
        operadorasRepository.saveAll(batch);
    }

}

