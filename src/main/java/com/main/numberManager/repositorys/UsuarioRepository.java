package com.main.numberManager.repositorys;

import com.main.numberManager.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel,Integer> {

}
