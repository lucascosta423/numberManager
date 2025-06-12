package com.main.numberManager.domain.users;

import com.main.numberManager.Enuns.Status;
import com.main.numberManager.Enuns.UserRole;
import com.main.numberManager.dtos.usuario.RequestSaveUsuarioDTO;
import com.main.numberManager.dtos.usuario.RequestUpdateUsuarioDTO;
import com.main.numberManager.dtos.usuario.ResponseUsuarioDto;
import com.main.numberManager.exeptions.NotFoundException;
import com.main.numberManager.domain.providers.ProviderService;
import com.main.numberManager.utils.responseApi.SucessResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
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
    public SucessResponse save(RequestSaveUsuarioDTO dto){

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(dto, userModel);

        String type = dto.tipoUsuario().toLowerCase();

        if (!type.equals("admin") && !type.equals("user")) {
            throw new IllegalArgumentException("Tipo de usuário inválido: " + dto.tipoUsuario());
        }

        fillDataUser(userModel, dto);
        usuarioRepository.save(userModel);

        String msg = type.equals("admin") ? "Administrador criado com sucesso" : "Usuário criado com sucesso";

        return new SucessResponse(msg, "OK");
    }

    public Page<ResponseUsuarioDto> findAllUsers(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(ResponseUsuarioDto::fromEntity);
    }

    public UserModel findByIdUser(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario não encontrado"));
    }

    @Transactional
    public SucessResponse updateUser(UUID id, RequestUpdateUsuarioDTO dto){

        UserModel usuario = findByIdUser(id);
        updateDataUser(usuario, dto);
        usuarioRepository.save(usuario);

        return new SucessResponse("Usuario atualizado","Ok");
    }

    private void fillDataUser(UserModel userModel,RequestSaveUsuarioDTO usuarioDTO){
        userModel.setStatus(Status.A);
        userModel.setSenha(cryptPassword(usuarioDTO.senha()));
        userModel.setRole(UserRole.valueOf(usuarioDTO.tipoUsuario().toUpperCase()));
        userModel.setProvedor(providerService.findById(usuarioDTO.provedor()));

    }

    private void updateDataUser(UserModel userModel, RequestUpdateUsuarioDTO dto){

        Optional.ofNullable(dto.getNome())
                .filter(nome -> !nome.trim().isEmpty())
                .ifPresent(userModel::setNome);

        Optional.ofNullable(dto.getEmail())
                .filter(email -> !email.trim().isEmpty())
                .ifPresent(userModel::setEmail);

        Optional.ofNullable(dto.getSenha())
                .filter(senha -> !senha.trim().isEmpty())
                .ifPresent(senha -> userModel.setSenha(cryptPassword(senha)));
    }

    private String cryptPassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    @Transactional
    public SucessResponse changeUserStatus(UUID id){
        var usuario = findByIdUser(id);

        switch (usuario.getStatus()){
            case A:
                usuario.setStatus(Status.I);
                return new SucessResponse("Usuario desativado com sucesso","OK");
            case I:
                usuario.setStatus(Status.A);
                return new SucessResponse("Usuario ativado com sucesso","OK");
        }

        usuarioRepository.save(usuario);

        return new SucessResponse("Nao foi possivel alterar o status do usuario","Erro");
    }
}
