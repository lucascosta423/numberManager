package com.main.numberManager.repositorys;

import com.main.numberManager.models.ProvedorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProvedorRepository extends JpaRepository<ProvedorModel,Integer> {

}
