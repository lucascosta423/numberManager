package com.main.numberManager.repositorys.usuario;

import com.main.numberManager.models.usuario.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel,Integer> {

}
