package com.main.numberManager.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.main.numberManager.Enuns.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "solicitacaoPortabilidade")
@Table(name = "SO_Portabilidade")
public class RequestPortabilityModel {

    @Id
    private String id;

    private String razao;

    private String documento;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    private LocalDateTime dataCriado;

    @PrePersist
    protected void onCreate() {
        this.dataCriado = LocalDateTime.now();
    }

    private LocalDateTime dataFinalizado;

    @ManyToOne
    @JoinColumn(name = "provedor_id", nullable = false)
    private ProviderModel provedor;

    @OneToMany(mappedBy = "solicitacaoPortabilidadeModel", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<NumberForPortabilityModel> numberForPortabilityModel;

    @Enumerated(EnumType.STRING)
    private Status status;
}
