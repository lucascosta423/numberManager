package com.main.numberManager.controllers.cnl;

import com.main.numberManager.dtos.cnl.CodigoAreaDTO;
import com.main.numberManager.models.cnl.CnlGeralModel;
import com.main.numberManager.services.cnl.CnlGeralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/CnlGeral")
public class CnlGeralController {

    private final CnlGeralService cnlGeralService;

    public CnlGeralController(CnlGeralService cnlGeralService) {
        this.cnlGeralService = cnlGeralService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file){
        try {
            cnlGeralService.processFile(file);
            return ResponseEntity.ok("File uploaded and processed successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file.");
        }
    }
    @GetMapping
    public ResponseEntity<Page<CnlGeralModel>> getByCodigoCnl(@PageableDefault(page = 0, size = 10,direction = Sort.Direction.ASC)Pageable pageable) {
        // O pageable contém informações sobre a página, como o número da página e o tamanho
        Page<CnlGeralModel> pageResult = cnlGeralService.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(pageResult);
    }

    @GetMapping("/cnl")
    public ResponseEntity<List<CnlGeralModel>> getByCodigoArea(@RequestBody CodigoAreaDTO codigoArea) {
        System.out.println(codigoArea);
        List<CnlGeralModel> cnlGeralModels = cnlGeralService.findByCodigoArea(codigoArea.codigoArea());

        if (cnlGeralModels.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cnlGeralModels);
    }

}
