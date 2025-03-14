package com.main.numberManager.services.serviceImpl;

import com.main.numberManager.models.cnl.CnlGeralModel;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IService<T>{

    @Async
    void processFile(MultipartFile file) throws IOException;

    @Transactional
    void saveBatch(List<T> batch);

}
