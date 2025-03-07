package com.main.numberManager.repositorys.numeros;

import com.main.numberManager.models.numero.NumeroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumeroRepository extends JpaRepository<NumeroModel,Integer> {
}
