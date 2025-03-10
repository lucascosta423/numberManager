package com.main.numberManager.repositorys.cnl;

import com.main.numberManager.models.cnl.CnlGeralModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CnlGeralRepository extends JpaRepository<CnlGeralModel,Integer> {

    List<CnlGeralModel> findByCodigoArea(String codigoArea);

}

