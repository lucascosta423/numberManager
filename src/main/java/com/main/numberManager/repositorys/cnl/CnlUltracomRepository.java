package com.main.numberManager.repositorys.cnl;

import com.main.numberManager.models.basesCnl.CnlUltracomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnlUltracomRepository extends JpaRepository<CnlUltracomModel,Integer> {
}
