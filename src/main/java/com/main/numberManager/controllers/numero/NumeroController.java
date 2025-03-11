package com.main.numberManager.controllers.numero;

import com.main.numberManager.services.numero.NumeroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/numero")
public class NumeroController {
    private final NumeroService numeroService;

    public NumeroController(NumeroService numeroService) {
        this.numeroService = numeroService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        try {
            numeroService.processFile(file);
            return ResponseEntity.ok("File uploaded and processed successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file.");
        }
    }
}
