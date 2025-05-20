package com.codagis.agischool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "perfis_usuario")
public class PerfilUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true)
    private TipoPerfil nome;

    public enum TipoPerfil {
        ROLE_ALUNO,
        ROLE_PROFESSOR,
        ROLE_ADMIN
    }

    public PerfilUsuario(TipoPerfil nome) {
        this.nome = nome;
    }
}