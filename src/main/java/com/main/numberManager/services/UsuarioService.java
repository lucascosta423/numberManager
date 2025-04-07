package com.main.numberManager.services;

import com.main.numberManager.dtos.usuario.RequestSaveUsuarioDTO;
import com.main.numberManager.dtos.usuario.RequestUpdateUsuarioDTO;
import com.main.numberManager.dtos.usuario.ResponseUsuarioDto;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.models.ProvedorModel;
import com.main.numberManager.models.UsuarioModel;
import com.main.numberManager.repositorys.UsuarioRepository;
import com.main.numberManager.utils.responseApi.SucessResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private  final ProvedorService provedorService;

    public UsuarioService(UsuarioRepository usuarioRepository, ProvedorService provedorService) {
        this.usuarioRepository = usuarioRepository;
        this.provedorService = provedorService;
    }

    public SucessResponse saveUser(RequestSaveUsuarioDTO usuarioDTO) {

        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(usuarioDTO,usuarioModel);

        ProvedorModel provedor = provedorService.findById(usuarioDTO.provedor())
                .orElseThrow(() -> new NotFoundException("Provedor não encontrado"));
        usuarioModel.setProvedor(provedor);

        return new SucessResponse("Usuario Cadastrado com sucesso.","OK");
    }

    public SucessResponse updateUser(UUID id, RequestUpdateUsuarioDTO dto){

        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario Nao encontrado"));

        if (dto.getNome() != null) usuario.setNome(dto.getNome());
        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
        if (dto.getSenha() != null) usuario.setSenha(dto.getSenha());

        usuarioRepository.save(usuario);

        return new SucessResponse("Usuario atualizado","Ok");
    }

    public Page<ResponseUsuarioDto> findAllUsers(Pageable pageable) {

        return usuarioRepository.findAll(pageable)
                .map(ResponseUsuarioDto::fromEntity);
    }

    public Optional<UsuarioModel> getByIdUser(UUID id) {
        return usuarioRepository.findById(id);
    }
}
