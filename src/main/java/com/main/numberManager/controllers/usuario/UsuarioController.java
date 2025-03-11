package com.main.numberManager.controllers.usuario;

import com.main.numberManager.dtos.usuario.UsuarioDTO;
import com.main.numberManager.models.provedor.ProvedorModel;
import com.main.numberManager.models.usuario.UsuarioModel;
import com.main.numberManager.repositorys.provedor.ProvedorRepository;
import com.main.numberManager.services.usuario.UsuarioService;
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

    @PostMapping("/new")
    public ResponseEntity<UsuarioModel> saveUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO){

        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(usuarioDTO,usuarioModel);
        ProvedorModel provedor = provedorRepository.findById(usuarioDTO.provedorId())
                .orElseThrow(() -> new RuntimeException("Provedor não encontrado"));

        usuarioModel.setProvedor(provedor);
       return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuarioModel));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<UsuarioModel>> getAllUsuarios(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    direction = Sort.Direction.ASC)
            Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll(pageable));
    }
}
