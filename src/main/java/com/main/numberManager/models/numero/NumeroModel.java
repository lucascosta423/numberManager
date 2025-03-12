package com.main.numberManager.models.numero;

import com.main.numberManager.models.provedor.ProvedorModel;
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
public class NumeroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,length = 2)
    private String cn;

    @Column(nullable = false,length = 4)
    private String prefixo;

    @Column(nullable = false,length = 4)
    private String mcdu;

    @Column(nullable = false,length = 20)
    private String area;

    private String cliente;

    private String documento;

    private LocalDateTime dataAtivacao;

    private LocalDateTime dataCancelamento;

    @ManyToOne
    @JoinColumn(name = "id_provedor")
    private ProvedorModel provedor;

    private String status;

}
