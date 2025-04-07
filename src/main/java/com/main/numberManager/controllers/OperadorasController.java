package com.main.numberManager.controllers;

import com.main.numberManager.dtos.operadoras.RequestNumeroOperadoraDTO;
import com.main.numberManager.dtos.operadoras.ResponseOperadorasDto;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.OperadorasModel;
import com.main.numberManager.services.OperadorasService;
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
    @GetMapping("/list")
    public ResponseEntity<Page<ResponseOperadorasDto>> findAll(@PageableDefault(page = 0, size = 20,direction = Sort.Direction.ASC)Pageable pageable) {
        Page<ResponseOperadorasDto> pageResult = operadorasService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(pageResult);
    }

    @GetMapping("/numero")
    public ResponseEntity<OperadorasModel> getByNumero(@RequestBody RequestNumeroOperadoraDTO numeroOperadoraDTO){

        var operadoraDTO = operadorasService.findByNumeroPortabilidade(
                numeroOperadoraDTO.prefixo(),
                numeroOperadoraDTO.mcdu(),
                numeroOperadoraDTO.codigoNacional()
        );

        return ResponseEntity.status(HttpStatus.OK).body(operadoraDTO);
    }

}
