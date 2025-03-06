package com.main.numberManager.repository.cnlParceiros;

import com.main.numberManager.model.basesCnl.CnlParceiros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface cnlParceirosRepository extends JpaRepository<CnlParceiros,Integer> {
}
