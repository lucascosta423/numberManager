package com.main.numberManager.repositorys;

import com.main.numberManager.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UserModel, UUID> {

    UserDetails findByUsuario(String usuario);

}
