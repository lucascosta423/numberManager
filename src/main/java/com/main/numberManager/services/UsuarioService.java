package com.main.numberManager.services;

import com.main.numberManager.dtos.usuario.ResponseUsuarioDto;
import com.main.numberManager.models.UsuarioModel;
import com.main.numberManager.repositorys.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioModel save(UsuarioModel usuarioModel) {
        return usuarioRepository.save(usuarioModel);
    }

    public Page<ResponseUsuarioDto> findAll(Pageable pageable) {

        return usuarioRepository.findAll(pageable)
                .map(ResponseUsuarioDto::fromEntity);
    }

    public Optional<UsuarioModel> getById(Integer id) {
        return usuarioRepository.findById(id);
    }
}
