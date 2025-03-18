package com.main.numberManager.models.numero;

import com.main.numberManager.models.provedor.ProvedorModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "numero")
@Table(name = "numero")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NumeroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 2)
    private Integer cn;

    @Column(nullable = false,length = 4)
    private Integer prefixo;

    @Column(nullable = false,length = 4)
    private Integer mcdu;

    @Column(nullable = false,length = 20)
    private String area;

    private String cliente;

    private String documento;

    private LocalDateTime dataAtivacao;

    private LocalDateTime dataCancelamento;

    @ManyToOne
    @JoinColumn(name = "id_provedor",nullable = false)
    private ProvedorModel provedor;

    private String status;

}
