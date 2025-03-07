package com.main.numberManager.services.cnl;

import com.main.numberManager.models.basesCnl.CnlGeralModel;
import com.main.numberManager.repositorys.cnl.CnlGeralRepository;
import com.main.numberManager.utils.CnlUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CnlGeralService {
    private final CnlGeralRepository cnlGeralRepository;

    public CnlGeralService(CnlGeralRepository cnlGeralRepository) {
        this.cnlGeralRepository = cnlGeralRepository;
    }

    public void processFile(MultipartFile file) {
        try {
            List<String> lines = CnlUtils.readerFile(file);
            List<CnlGeralModel> cnlGeralModels = CnlUtils.parseLines(lines,
                    line -> CnlUtils.mapToModel(line, CnlGeralModel.class));
            cnlGeralRepository.saveAll(cnlGeralModels);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}

