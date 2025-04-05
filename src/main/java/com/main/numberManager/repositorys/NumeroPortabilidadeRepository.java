package com.main.numberManager.repositorys;

import com.main.numberManager.models.NumeroPortabilidadeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NumeroPortabilidadeRepository extends JpaRepository<NumeroPortabilidadeModel, UUID> {
}
