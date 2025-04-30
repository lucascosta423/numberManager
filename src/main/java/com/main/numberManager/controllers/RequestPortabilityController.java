package com.main.numberManager.controllers;

import com.main.numberManager.dtos.Portability.UpdateDocumentOrReason;
import com.main.numberManager.dtos.Portability.ResponsePortabilityDTO;
import com.main.numberManager.dtos.Portability.RequestPortabilityDTO;
import com.main.numberManager.dtos.Portability.UpdateNumberForPortabilityDTO;
import com.main.numberManager.services.RequestPortabilityService;
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
public class RequestPortabilityController {

    private final RequestPortabilityService requestPortabilityService;


    public RequestPortabilityController(RequestPortabilityService requestPortabilityService){
        this.requestPortabilityService = requestPortabilityService;

    }

    @PostMapping("/save")
    public ResponseEntity<SucessResponse> save(@RequestBody @Valid RequestPortabilityDTO portabilidadeDTO){
        return ResponseEntity.status(HttpStatus.OK).body(requestPortabilityService.save(portabilidadeDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SucessResponse> updateDocumentOrReason(@PathVariable(value = "id") String id, @RequestBody UpdateDocumentOrReason dto){
        return ResponseEntity.status(HttpStatus.OK).body(requestPortabilityService.updateDocumentOrReason(id, dto));
    }

    @PutMapping("/update/status/{id}")
    public ResponseEntity<SucessResponse> updateStatus(@PathVariable(value = "id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(requestPortabilityService.updateStatus(id));
    }

    @PutMapping("/update/number/{id}")
    public ResponseEntity<SucessResponse> updateNumber(@PathVariable(value = "id") String id, @RequestBody UpdateNumberForPortabilityDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(requestPortabilityService.updateNumber(id,dto));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ResponsePortabilityDTO>> listAll(@PageableDefault(
            page = 0,
            size = 20,
            direction = Sort.Direction.ASC)
                                                                 Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(requestPortabilityService.findAll(pageable));
    }
}
