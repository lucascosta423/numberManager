package com.main.numberManager.controllers.provedor;

import com.main.numberManager.dtos.provedor.ProvedorDTO;
import com.main.numberManager.models.provedor.ProvedorModel;
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

import java.util.Optional;

@RestController
@RequestMapping("/provedor")
public class provedorController {

    private final ProvedorService provedorService;


    public provedorController(ProvedorService provedorService) {
        this.provedorService = provedorService;
    }

    @PostMapping("/new")
    public ResponseEntity<ProvedorModel> saveProvedor(@RequestBody @Valid ProvedorDTO provedorDTO){
            var model = new ProvedorModel();

            BeanUtils.copyProperties(provedorDTO, model);

            return ResponseEntity.status(HttpStatus.CREATED).body(provedorService.save(model));
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
    public ResponseEntity<Object> getById(@PathVariable(value = "id") Integer id){
        Optional<ProvedorModel> provedorModelOptional = provedorService.findById(id);

        return provedorModelOptional.<ResponseEntity<Object>>map(
                provedorModel -> ResponseEntity.status(HttpStatus.OK).body(provedorModel))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Provedor Não encontrado"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProvedor(@PathVariable(value = "id") Integer id,
                                                  @RequestBody @Valid ProvedorDTO provedorDTO){
        Optional<ProvedorModel> provedorModelOptional = provedorService.findById(id);
        if (provedorModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Provedor Não encontrado");
        }
        var provedorModel = new ProvedorModel();
        BeanUtils.copyProperties(provedorDTO,provedorModel);
        provedorModel.setId(provedorModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(provedorService.save(provedorModel));
    }
}
