package com.main.numberManager.repositorys.cnl;

import com.main.numberManager.models.basesCnl.CnlParceirosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnlParceirosRepository extends JpaRepository<CnlParceirosModel,Integer> {
}
