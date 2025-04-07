package com.main.numberManager.controllers;

import com.main.numberManager.dtos.numero.RequestNumeroDTO;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.NumeroModel;
import com.main.numberManager.models.ProvedorModel;
import com.main.numberManager.services.NumeroService;
import com.main.numberManager.services.ProvedorService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/numero")
public class NumeroController {
    private final NumeroService numeroService;
    private final ProvedorService provedorService;

    public NumeroController(NumeroService numeroService, ProvedorService provedorService) {
        this.numeroService = numeroService;
        this.provedorService = provedorService;
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

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateNumero(@PathVariable(value = "id") Integer id,
                                               @RequestBody @Valid RequestNumeroDTO requestNumeroDTO){

        NumeroModel numeroModel = numeroService.findById(id);

        ProvedorModel provedorModel = provedorService.findById(requestNumeroDTO.idProvedor());
        numeroModel.setProvedor(provedorModel);

        numeroModel.setDataAtivacao(LocalDateTime.now());

        BeanUtils.copyProperties(
                requestNumeroDTO,
                numeroModel,
                "id","cn","mcdu","area","provedor"
        );
        return ResponseEntity.status(HttpStatus.OK).body(numeroService.save(numeroModel));
    }

    @GetMapping
    public ResponseEntity<Page<NumeroModel>> getAllNumeros(
            @PageableDefault(page = 0,
                    size = 10,
                    direction = Sort.Direction.ASC)
            Pageable pageable){

        return ResponseEntity.status(HttpStatus.OK).body(numeroService.findAll(pageable));
    }
}
