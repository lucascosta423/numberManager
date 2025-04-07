package com.main.numberManager.models;

import com.main.numberManager.Enuns.Status;
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
    @PrePersist
    private void onCreate(){
        this.dataAtivacao = LocalDateTime.now();
    }

    private LocalDateTime dataCancelamento;
    @PreRemove
    private void onRemove(){
        this.dataCancelamento = LocalDateTime.now();
    }


    @ManyToOne
    @JoinColumn(name = "id_provedor",nullable = false)
    private ProvedorModel provedor;

    @Enumerated(EnumType.STRING)
    private Status status;

}
