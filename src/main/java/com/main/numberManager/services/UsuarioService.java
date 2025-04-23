package com.main.numberManager.services;

import com.main.numberManager.Enuns.Status;
import com.main.numberManager.Enuns.UserRole;
import com.main.numberManager.dtos.usuario.RequestSaveUsuarioAdmDTO;
import com.main.numberManager.dtos.usuario.RequestSaveUsuarioDTO;
import com.main.numberManager.dtos.usuario.RequestUpdateUsuarioDTO;
import com.main.numberManager.dtos.usuario.ResponseUsuarioDto;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.UsuarioModel;
import com.main.numberManager.repositorys.UsuarioRepository;
import com.main.numberManager.utils.responseApi.SucessResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private  final ProviderService providerService;

    public UsuarioService(UsuarioRepository usuarioRepository, ProviderService providerService) {
        this.usuarioRepository = usuarioRepository;
        this.providerService = providerService;
    }

    @Transactional
    public SucessResponse saveUser(RequestSaveUsuarioDTO usuarioDTO) {

        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(usuarioDTO,usuarioModel);

        String encryptedPassword = new BCryptPasswordEncoder().encode(usuarioDTO.senha());
        usuarioModel.setSenha(encryptedPassword);

        usuarioModel.setStatus(Status.A);

        usuarioModel.setRole(UserRole.USER);

        usuarioModel.setProvedor(providerService.findById(usuarioDTO.provedor()));


        usuarioRepository.save(usuarioModel);

        return new SucessResponse("Usuario Cadastrado com sucesso.","OK");
    }

    @Transactional
    public SucessResponse saveAdmin(RequestSaveUsuarioAdmDTO adminDTO) {

        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(adminDTO,usuarioModel);

        String encryptedPassword = new BCryptPasswordEncoder().encode(adminDTO.senha());
        usuarioModel.setSenha(encryptedPassword);

        usuarioModel.setStatus(Status.A);

        usuarioModel.setRole(UserRole.ADMIN);

        usuarioRepository.save(usuarioModel);

        return new SucessResponse("Administrador Cadastrado com sucesso.","OK");
    }

    @Transactional
    public SucessResponse updateUser(UUID id, RequestUpdateUsuarioDTO dto){

        UsuarioModel usuario = findByIdUser(id);

        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
        if (dto.getSenha() != null) usuario.setSenha(dto.getSenha());

        usuarioRepository.save(usuario);

        return new SucessResponse("Usuario atualizado","Ok");
    }

    @Transactional
    public SucessResponse changeProviderStatus(UUID id){
        var usuario = findByIdUser(id);

        switch (usuario.getStatus()){
            case A -> usuario.setStatus(Status.I);
            case I -> usuario.setStatus(Status.A);
        }

        usuarioRepository.save(usuario);

        return new SucessResponse("Usuario Deletado ou Reativado Com Sucesso","OK");
    }

    public Page<ResponseUsuarioDto> findAllUsers(Pageable pageable) {

        return usuarioRepository.findAll(pageable)
                .map(ResponseUsuarioDto::fromEntity);
    }

    public UsuarioModel findByIdUser(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario não encontrado"));
    }
}
