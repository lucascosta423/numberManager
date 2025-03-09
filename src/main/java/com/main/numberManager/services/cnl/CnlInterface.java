package com.main.numberManager.services.cnl;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CnlInterface {
    void processFile(MultipartFile file) throws IOException;
}
