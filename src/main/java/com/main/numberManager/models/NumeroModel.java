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
    @PrePersist
    private void onCreate(){
        this.dataAtivacao = LocalDateTime.now();
    }

    private LocalDateTime dataUpdate;
    @PreUpdate
    private void onUpdate(){
        this.dataUpdate = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "id_provedor")
    private ProvedorModel provedor;

    @Enumerated(EnumType.STRING)
    private Status status;

}
