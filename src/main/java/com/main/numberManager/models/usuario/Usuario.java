package com.main.numberManager.models.usuario;

import com.main.numberManager.models.provedores.Provedor;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_USUARIOS")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "provedor_id", nullable = false)
    private Provedor provedor;
    private String nome;
    private String email;
    private String usuario;
    private String senha;
}
