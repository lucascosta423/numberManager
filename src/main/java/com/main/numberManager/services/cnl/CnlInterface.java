package com.main.numberManager.services.cnl;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CnlInterface<T>{
    @Async
    void processFile(MultipartFile file) throws IOException;

    @Transactional
    void saveBatch(List<T> batch);
}
