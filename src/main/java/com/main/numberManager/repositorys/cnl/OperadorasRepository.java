package com.main.numberManager.repositorys.cnl;

import com.main.numberManager.models.operadoras.Operadoras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperadorasRepository extends JpaRepository<Operadoras,Integer> {

    @Query("SELECT b FROM Operadoras b WHERE " +
            "b.prefixo = :prefixo " +
            "AND :valor BETWEEN b.faixaInicial AND b.faixaFinal " +
            "AND b.codigoNacional = :codigoNacional")
    Optional<Operadoras> findNumero(
            @Param("prefixo") Integer prefixo,
            @Param("valor") Integer valor,
            @Param("codigoNacional") Integer codigoNacional);

}

