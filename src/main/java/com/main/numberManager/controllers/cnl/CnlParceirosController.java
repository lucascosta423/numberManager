package com.main.numberManager.controllers.cnl;

import com.main.numberManager.services.cnl.CnlParceirosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/CnlParceiros")
public class CnlParceirosController {

    private final CnlParceirosService cnlParceirosService;

    public CnlParceirosController(CnlParceirosService cnlParceirosService) {
        this.cnlParceirosService = cnlParceirosService;
    }

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file){
        try {
            cnlParceirosService.processFile(file);
            return ResponseEntity.ok("File uploaded and processed successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file.");
        }
    }

}
