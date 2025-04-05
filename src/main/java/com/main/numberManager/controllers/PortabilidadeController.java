package com.main.numberManager.controllers;

import com.main.numberManager.dtos.portabilidade.RequestPortabilidadeDTO;
import com.main.numberManager.models.PortabilidadeModel;
import com.main.numberManager.services.PortabilidadeService;
import com.main.numberManager.services.ProvedorService;
import com.main.numberManager.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portabilidade")
public class PortabilidadeController {

    private final PortabilidadeService portabilidadeService;


    public PortabilidadeController(PortabilidadeService portabilidadeService){
        this.portabilidadeService = portabilidadeService;

    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody @Valid RequestPortabilidadeDTO portabilidadeDTO){

        return ResponseEntity.status(HttpStatus.OK).body(portabilidadeService.save(portabilidadeDTO));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<PortabilidadeModel>> listAll( @PageableDefault(
            page = 0,
            size = 10,
            direction = Sort.Direction.ASC)
                                                                 Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(portabilidadeService.findAll(pageable));
    }
}
