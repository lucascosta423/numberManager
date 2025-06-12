package com.main.numberManager.domain.users;

import com.main.numberManager.dtos.usuario.RequestSaveUsuarioDTO;
import com.main.numberManager.dtos.usuario.RequestUpdateUsuarioDTO;
import com.main.numberManager.dtos.usuario.ResponseUsuarioDto;
import com.main.numberManager.domain.providers.ProviderService;
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

import java.util.UUID;

@Tag(name = "Usuario", description = "Gerenciamento de usuarios")
@RestController
@RequestMapping("/usuario")

public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService, ProviderService providerService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/save/user")
    public ResponseEntity<SucessResponse> saveUsuario(@RequestBody @Valid RequestSaveUsuarioDTO requestSaveUsuarioDTO){

        var sucess = usuarioService.save(requestSaveUsuarioDTO);

       return ResponseEntity.status(HttpStatus.CREATED).body(sucess);
    }

    @GetMapping("/listAll")
    public ResponseEntity<Page<ResponseUsuarioDto>> getAllUsuarios(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    direction = Sort.Direction.ASC)
            Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAllUsers(pageable));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<SucessResponse> updateUser(@PathVariable(value = "id") UUID id,
                                                     @Valid @RequestBody RequestUpdateUsuarioDTO usuarioDto){

        SucessResponse response = usuarioService.updateUser(id,usuarioDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<SucessResponse> changeProviderStatus(@PathVariable(value = "id") UUID id){
        SucessResponse response = usuarioService.changeUserStatus(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
