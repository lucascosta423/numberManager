package com.main.numberManager.services.cnl;

import com.main.numberManager.models.basesCnl.CnlUltracomModel;
import com.main.numberManager.repositorys.cnl.CnlUltracomRepository;
import com.main.numberManager.utils.CnlUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CnlUltracomService implements CnlInterface{
    private final CnlUltracomRepository cnlUltracomRepository;

    public CnlUltracomService(CnlUltracomRepository cnlUltracomRepository) {
        this.cnlUltracomRepository = cnlUltracomRepository;
    }

    @Override
    public void processFile(MultipartFile file) throws IOException {
//        List<String> lines = CnlUtils.readerFile(file);
//        List<CnlUltracomModel> cnlUltracomModels = CnlUtils.parseLines(lines,
//                line -> CnlUtils.mapToModel(line, CnlUltracomModel.class));
//        cnlUltracomRepository.saveAll(cnlUltracomModels);
    }
}
