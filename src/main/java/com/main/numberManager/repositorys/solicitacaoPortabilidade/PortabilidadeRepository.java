package com.main.numberManager.repositorys.solicitacaoPortabilidade;

import com.main.numberManager.models.portabilidade.PortabilidadeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortabilidadeRepository extends JpaRepository<PortabilidadeModel, Integer> {

}
