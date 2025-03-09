package com.main.numberManager.services.cnl;

import com.main.numberManager.models.basesCnl.CnlParceirosModel;
import com.main.numberManager.repositorys.cnl.CnlParceirosRepository;
import com.main.numberManager.utils.CnlUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CnlParceirosService implements CnlInterface{

    private final CnlParceirosRepository cnlParceirosRepository;

    public CnlParceirosService(CnlParceirosRepository cnlParceirosRepository) {
        this.cnlParceirosRepository = cnlParceirosRepository;
    }

    @Override
    public void processFile(MultipartFile file) throws IOException {
//        List<String> lines = CnlUtils.readerFile(file);
//        List<CnlParceirosModel> cnlGeralModels = CnlUtils.parseLines(lines,
//                line -> CnlUtils.mapToModel(line, CnlParceirosModel.class));
//        cnlParceirosRepository.saveAll(cnlGeralModels);
    }
}
