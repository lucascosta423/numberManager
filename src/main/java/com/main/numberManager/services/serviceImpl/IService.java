package com.main.numberManager.services.serviceImpl;


import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IService<T>{

    @Async
    void processFile(MultipartFile file) throws IOException;

    @Transactional
    void saveBatch(List<T> batch);

}
