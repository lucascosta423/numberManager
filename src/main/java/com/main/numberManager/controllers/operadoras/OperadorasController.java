package com.main.numberManager.controllers.operadoras;

import com.main.numberManager.models.operadoras.Operadoras;
import com.main.numberManager.services.operadoras.OperadorasService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/operadoras")
public class OperadorasController {

    private final OperadorasService operadorasService;

    public OperadorasController(OperadorasService operadorasService) {
        this.operadorasService = operadorasService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file){
        try {
            operadorasService.processFile(file);
            return ResponseEntity.ok("File uploaded and processed successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file.");
        }
    }
    @GetMapping
    public ResponseEntity<Page<Operadoras>> getByCodigoCnl(@PageableDefault(page = 0, size = 10,direction = Sort.Direction.ASC)Pageable pageable) {
        Page<Operadoras> pageResult = operadorasService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(pageResult);
    }

}
