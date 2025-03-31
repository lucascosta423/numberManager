package com.main.numberManager.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "provedor")
@Table(name = "provedor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProvedorModel {
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
}
