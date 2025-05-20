package com.codagis.agischool.repository;

import com.codagis.agischool.enums.TipoPerfil;
import com.codagis.agischool.model.Perfil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    Optional<Perfil> findByTipo(TipoPerfil tipo);
    boolean existsByTipo(TipoPerfil tipo);
}