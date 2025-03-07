package com.main.numberManager.repositorys.cnl;

import com.main.numberManager.models.basesCnl.CnlGeralModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnlGeralRepository extends JpaRepository<CnlGeralModel,Integer> {
}
