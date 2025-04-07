package com.main.numberManager.services;

import com.main.numberManager.Enuns.Status;
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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public SucessResponse saveUser(RequestSaveUsuarioDTO usuarioDTO) {

        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(usuarioDTO,usuarioModel);

        var status = Status.A;
        usuarioModel.setStatus(status);

        ProvedorModel provedor = provedorService.findById(usuarioDTO.provedor());
        usuarioModel.setProvedor(provedor);

        usuarioRepository.save(usuarioModel);

        return new SucessResponse("Usuario Cadastrado com sucesso.","OK");
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

    public SucessResponse deleteUser(UUID id){
        var usuario = findByIdUser(id);
        var statusInativo = Status.I;

        if (!usuario.getStatus().equals(statusInativo)){
            usuario.setStatus(statusInativo);
        }else {
            var statusAtivo = Status.A;
            usuario.setStatus(statusAtivo);
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
