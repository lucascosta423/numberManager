package com.main.numberManager.domain.providers;

import com.main.numberManager.Enuns.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity(name = "provedor")
@Table(name = "provedor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true,length = 150)
    private String nome;

    @Column(nullable = false,unique = true,length = 11)
    private String contato;

    @Column(nullable = false,unique = true,length = 15)
    private String documento;

    @Column(nullable = false,unique = true,length = 70)
    private String email;

    private LocalDateTime dataCriado;
    @PrePersist
    protected void onCreate() {
        this.dataCriado = LocalDateTime.now();
    }

    private LocalDateTime dataAtualizacao;
    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }


    @Enumerated(EnumType.STRING)
    private Status status;

    public boolean verifyStatus(){
        return this.status == Status.I;
    }
}
