package com.main.numberManager.repositorys;

import com.main.numberManager.models.NumeroModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumeroRepository extends JpaRepository<NumeroModel,Integer> {
}
