package com.main.numberManager.models.provedor;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;


@Entity(name = "provedor")
@Table(name = "TB_PROVEDOR")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProvedorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String contato;
    private String documento;
    private String email;
}
