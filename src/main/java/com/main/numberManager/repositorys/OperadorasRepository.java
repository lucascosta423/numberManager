package com.main.numberManager.repositorys;

import com.main.numberManager.models.OperadorasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperadorasRepository extends JpaRepository<OperadorasModel,Integer> {

    @Query("SELECT b FROM OperadorasModel b WHERE " +
            "b.prefixo = :prefixo " +
            "AND :mcdu BETWEEN b.faixaInicial AND b.faixaFinal " +
            "AND b.codigoNacional = :codigoNacional")
    Optional<OperadorasModel> findByCodigoCnl(
            @Param("prefixo") String prefixo,
            @Param("mcdu") String mcdu,
            @Param("codigoNacional") String codigoNacional);

}

