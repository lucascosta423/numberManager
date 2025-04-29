package com.main.numberManager.controllers;

import com.main.numberManager.dtos.Number.RequestNumberUpdateDTO;
import com.main.numberManager.dtos.Number.RequestReserveNumberDTO;
import com.main.numberManager.dtos.Number.ResponseAllNumbersDto;
import com.main.numberManager.models.UsuarioModel;
import com.main.numberManager.services.FilesUpload.NumberFilesService;
import com.main.numberManager.services.NumeroService;
import com.main.numberManager.utils.responseApi.SucessResponse;
import jakarta.validation.Valid;
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
@RequestMapping("/numero")
public class NumberController {
    private final NumeroService numeroService;
    private final NumberFilesService numberFilesService;

    public NumberController(NumeroService numeroService, NumberFilesService numberFilesService) {
        this.numeroService = numeroService;
        this.numberFilesService = numberFilesService;
    }

    @PostMapping("upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){
        try {
            numberFilesService.processFile(file);
            return ResponseEntity.ok("File uploaded and processed successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file.");
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<SucessResponse> activateNumber(@PathVariable(value = "id") Integer id, @RequestBody @Valid RequestNumberUpdateDTO dto){

        var success = numeroService.activateNumber(id,dto);

        return ResponseEntity.status(HttpStatus.OK).body(success);
    }

    @PutMapping("reserve")
    public ResponseEntity<SucessResponse> reserveNumber(@RequestBody @Valid RequestReserveNumberDTO dto){

        var success = numeroService.reserveNumber(dto);

        return ResponseEntity.status(HttpStatus.OK).body(success);
    }

    @GetMapping("list")
    public ResponseEntity<Page<ResponseAllNumbersDto>> getAllNumeros(
            @PageableDefault(page = 0,size = 10,direction = Sort.Direction.ASC) Pageable pageable){

        return ResponseEntity.status(HttpStatus.OK).body(numeroService.findAll(pageable));
    }
}
