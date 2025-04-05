package com.main.numberManager.repositorys;

import com.main.numberManager.dtos.operadoras.ResponseNumeroPortabilidadeDTO;
import com.main.numberManager.models.OperadorasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperadorasRepository extends JpaRepository<OperadorasModel,Integer> {

    @Query("SELECT b.codigoCnl FROM OperadorasModel b WHERE " +
            "b.prefixo = :prefixo " +
            "AND :mcdu BETWEEN b.faixaInicial AND b.faixaFinal " +
            "AND b.codigoNacional = :codigoNacional")
    Optional<ResponseNumeroPortabilidadeDTO> findByCodigoCnl(
            @Param("prefixo") Integer prefixo,
            @Param("mcdu") Integer mcdu,
            @Param("codigoNacional") Integer codigoNacional);

}

