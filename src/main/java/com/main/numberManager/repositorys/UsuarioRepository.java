package com.main.numberManager.repositorys;

import com.main.numberManager.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID> {

    UserDetails findByUsuario(String usuario);

}
