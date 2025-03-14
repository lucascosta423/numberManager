package com.main.numberManager.controllers.numero;

import com.main.numberManager.dtos.numero.RequestNumeroDTO;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.numero.NumeroModel;
import com.main.numberManager.models.provedor.ProvedorModel;
import com.main.numberManager.services.numero.NumeroService;
import com.main.numberManager.services.provedor.ProvedorService;
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
import java.util.Optional;

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

        NumeroModel numeroModel = numeroService.findById(id)
                .orElseThrow(() -> new NotFoundException("Numero não encontrado"));

        ProvedorModel provedorModel = provedorService.findById(requestNumeroDTO.idProvedor())
                .orElseThrow(() -> new NotFoundException("Provedor não encontrado"));
        numeroModel.setProvedor(provedorModel);

        BeanUtils.copyProperties(
                requestNumeroDTO,
                numeroModel,
                "id","cn","mcdu","area","provedor"
        );
        return ResponseEntity.status(HttpStatus.OK).body(numeroService.save(numeroModel));
    }

    @GetMapping
    public ResponseEntity<Page<NumeroModel>> getALlProvedor(
            @PageableDefault(page = 0,
                    size = 10,
                    direction = Sort.Direction.ASC)
            Pageable pageable){

        return ResponseEntity.status(HttpStatus.OK).body(numeroService.findAll(pageable));
    }
}
