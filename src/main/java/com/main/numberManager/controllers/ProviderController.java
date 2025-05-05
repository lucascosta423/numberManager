package com.main.numberManager.controllers;

import com.main.numberManager.dtos.provider.RequestProviderDTO;
import com.main.numberManager.models.ProviderModel;
import com.main.numberManager.services.ProviderService;
import com.main.numberManager.utils.responseApi.SucessResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Provedor", description = "API REST para gerenciamento de provedores")
@RestController
@RequestMapping("/provedor")
public class ProviderController {

    private final ProviderService providerService;
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping("/save")
    public ResponseEntity<SucessResponse> saveProvedor(@RequestBody @Valid RequestProviderDTO requestProviderDTO){

        var success = providerService.save(requestProviderDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(success);
    }

    @GetMapping("/listAll")
    public ResponseEntity<Page<ProviderModel>> getALlProvedor(
            @PageableDefault(page = 0,
                    size = 10,
                    direction = Sort.Direction.ASC)
            Pageable pageable){

        return ResponseEntity.status(HttpStatus.OK).body(providerService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProviderModel> getById(@PathVariable(value = "id") Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(providerService.findById(id));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<SucessResponse> updateProvedor(@PathVariable(value = "id") Integer id,
                                                 @RequestBody @Valid RequestProviderDTO requestProviderDTO){
        var success = providerService.updateProvedor(id, requestProviderDTO);

        return ResponseEntity.status(HttpStatus.OK).body(success);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<SucessResponse> changeProviderStatus(@PathVariable(value = "id") Integer id){
        var success = providerService.changeProviderStatus(id);
        return ResponseEntity.status(HttpStatus.OK).body(success);
    }
}
