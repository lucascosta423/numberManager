package com.main.numberManager.controllers;

import com.main.numberManager.dtos.provedor.ProvedorDTO;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.ProvedorModel;
import com.main.numberManager.services.ProvedorService;
import com.main.numberManager.utils.responseApi.SucessResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/provedor")
public class provedorController {

    private final ProvedorService provedorService;
    public provedorController(ProvedorService provedorService) {
        this.provedorService = provedorService;
    }

    @PostMapping("/save")
    public ResponseEntity<SucessResponse> saveProvedor(@RequestBody @Valid ProvedorDTO provedorDTO){

        var success = provedorService.save(provedorDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(success);
    }

    @GetMapping
    public ResponseEntity<Page<ProvedorModel>> getALlProvedor(
            @PageableDefault(page = 0,
                    size = 10,
                    direction = Sort.Direction.ASC)
            Pageable pageable){

        return ResponseEntity.status(HttpStatus.OK).body(provedorService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvedorModel> getById(@PathVariable(value = "id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(provedorService.findById(id));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<SucessResponse> updateProvedor(@PathVariable(value = "id") Integer id,
                                                 @RequestBody @Valid ProvedorDTO provedorDTO){
        var success = provedorService.updateProvedor(id, provedorDTO);

        return ResponseEntity.status(HttpStatus.OK).body(success);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<SucessResponse> inativaProvedor(@PathVariable(value = "id") Integer id){
        var success = provedorService.deleteProvedor(id);
        return ResponseEntity.status(HttpStatus.OK).body(success);
    }
}
