package com.main.numberManager.repositorys;

import com.main.numberManager.models.OperatorsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperatorsRepository extends JpaRepository<OperatorsModel,Integer> {

    @Query("SELECT b FROM OperatorsModel b WHERE " +
            "b.prefixo = :prefixo " +
            "AND :mcdu BETWEEN b.faixaInicial AND b.faixaFinal " +
            "AND b.codigoNacional = :codigoNacional")
    Optional<OperatorsModel> findByNumero(
            @Param("codigoNacional") String codigoNacional,
            @Param("prefixo") String prefixo,
            @Param("mcdu") String mcdu
    );

}

