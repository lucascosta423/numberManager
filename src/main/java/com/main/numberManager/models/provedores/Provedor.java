package com.main.numberManager.models.provedores;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "TB_PROVEDOR")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Provedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String documento;
    private String email;
}
