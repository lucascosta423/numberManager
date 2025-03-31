package com.main.numberManager.controllers;

import com.main.numberManager.dtos.usuario.ResponseUsuarioDto;
import com.main.numberManager.dtos.usuario.RequestSaveUsuarioDTO;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.ProvedorModel;
import com.main.numberManager.models.UsuarioModel;
import com.main.numberManager.repositorys.ProvedorRepository;
import com.main.numberManager.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")

public class UsuarioController {
    private final UsuarioService usuarioService;
    private final ProvedorRepository provedorRepository;

    public UsuarioController(UsuarioService usuarioService, ProvedorRepository provedorRepository) {
        this.usuarioService = usuarioService;
        this.provedorRepository = provedorRepository;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveUsuario(@RequestBody @Valid RequestSaveUsuarioDTO requestSaveUsuarioDTO){

        ProvedorModel provedor = provedorRepository.findById(requestSaveUsuarioDTO.provedor())
                .orElseThrow(() -> new NotFoundException("Provedor não encontrado"));

        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(requestSaveUsuarioDTO,usuarioModel);

        usuarioModel.setProvedor(provedor);
        usuarioService.save(usuarioModel);
       return ResponseEntity.status(HttpStatus.CREATED).body("Usuario criado com sucesso!");
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ResponseUsuarioDto>> getAllUsuarios(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    direction = Sort.Direction.ASC)
            Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll(pageable));
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateUser(@PathVariable(value = "id") Integer id,
                                             @Valid @RequestBody RequestSaveUsuarioDTO requestSaveUsuarioDTO){
        UsuarioModel usuario = usuarioService.getById(id)
                .orElseThrow(() -> new NotFoundException("Provedor não encontrado"));

        BeanUtils.copyProperties(requestSaveUsuarioDTO,usuario,"id");
        usuarioService.save(usuario);

        return ResponseEntity.status(HttpStatus.OK).body("Usuario Atualizado com sucesso!");
    }
}
