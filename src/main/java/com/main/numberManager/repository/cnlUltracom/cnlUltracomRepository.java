package com.main.numberManager.repository.cnlUltracom;

import com.main.numberManager.model.basesCnl.CnlUltracom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cnlUltracomRepository extends JpaRepository<CnlUltracom,Integer> {
}
