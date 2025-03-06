package com.main.numberManager.repository.cnlGeral;

import com.main.numberManager.model.basesCnl.CnlGeral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnlGeralRepository extends JpaRepository<CnlGeral,Integer> {
}
