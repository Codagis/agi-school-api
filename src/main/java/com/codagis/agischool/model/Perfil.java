package com.codagis.agischool.model;

import com.codagis.agischool.enums.TipoPerfil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "perfis")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private TipoPerfil tipo;

    private String descricao;

    public Perfil(TipoPerfil tipo, String descricao) {
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public GrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority(this.tipo.name());
    }
}