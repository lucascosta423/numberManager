package com.main.numberManager.services.interfaceImpl;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InterfaceImpl<T>{

    @Async
    void processFile(MultipartFile file) throws IOException;

    @Transactional
    void saveBatch(List<T> batch);

}
