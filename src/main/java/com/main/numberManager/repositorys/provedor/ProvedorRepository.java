package com.main.numberManager.repositorys.provedor;

import com.main.numberManager.models.provedor.ProvedorModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvedorRepository extends JpaRepository<ProvedorModel,Integer> {

}
