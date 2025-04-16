package com.main.numberManager.controllers;

import com.main.numberManager.dtos.portabilidade.ResponseSolicitacaoPortabilidadeDto;
import com.main.numberManager.dtos.portabilidade.SolicitacaoPortabilidadeDTO;
import com.main.numberManager.models.SolicitacaoPortabilidadeModel;
import com.main.numberManager.services.SolicitacaoPortabilidadeService;
import com.main.numberManager.utils.responseApi.SucessResponse;
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
public class SolicitacaoPortabilidadeController {

    private final SolicitacaoPortabilidadeService solicitacaoPortabilidadeService;


    public SolicitacaoPortabilidadeController(SolicitacaoPortabilidadeService solicitacaoPortabilidadeService){
        this.solicitacaoPortabilidadeService = solicitacaoPortabilidadeService;

    }

    @PostMapping("/save")
    public ResponseEntity<SucessResponse> save(@RequestBody @Valid SolicitacaoPortabilidadeDTO portabilidadeDTO){
        return ResponseEntity.status(HttpStatus.OK).body(solicitacaoPortabilidadeService.save(portabilidadeDTO));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ResponseSolicitacaoPortabilidadeDto>> listAll(@PageableDefault(
            page = 0,
            size = 20,
            direction = Sort.Direction.ASC)
                                                                 Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(solicitacaoPortabilidadeService.findAll(pageable));
    }
}
