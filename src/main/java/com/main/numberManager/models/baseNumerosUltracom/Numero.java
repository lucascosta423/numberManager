package com.main.numberManager.models.baseNumerosUltracom;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_NUMEROS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Numero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer idProvedor;

    private String numero;

    private String regiao;

    private String cliente;

    private String documento;

    private LocalDateTime dataAtivacao;

    private LocalDateTime dataCancelamento;

    private String status;
}
