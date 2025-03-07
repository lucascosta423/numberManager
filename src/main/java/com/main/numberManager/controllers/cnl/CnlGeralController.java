package com.main.numberManager.controllers.cnl;

import com.main.numberManager.services.cnl.CnlGeralService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/CnlGeral")
public class CnlGeralController {

    private final CnlGeralService cnlGeralService;

    public CnlGeralController(CnlGeralService cnlGeralService) {
        this.cnlGeralService = cnlGeralService;
    }

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file){
        try {
            cnlGeralService.processFile(file);
            return ResponseEntity.ok("File uploaded and processed successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file.");
        }
    }

}
