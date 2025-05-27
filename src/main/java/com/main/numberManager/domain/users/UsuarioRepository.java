package com.main.numberManager.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UserModel, UUID> {

    UserDetails findByUsuario(String usuario);

}
