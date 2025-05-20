package com.codagis.agischool.service;

import com.codagis.agischool.enums.TipoPerfil;
import com.codagis.agischool.model.Perfil;
import com.codagis.agischool.repository.PerfilRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepository;

    @PostConstruct
    public void initPerfis() {
        Arrays.stream(TipoPerfil.values()).forEach(tipo -> {
            if (!perfilRepository.existsByTipo((tipo))) {
                String descricao = switch (tipo) {
                    case ROLE_ALUNO -> "Perfil de aluno";
                    case ROLE_PROFESSOR -> "Perfil de professor";
                    case ROLE_ADMIN -> "Perfil de administrador";
                };
                perfilRepository.save(new Perfil(tipo, descricao));
            }
        });
    }

    public Perfil buscarPorTipo(TipoPerfil tipo) {
        return perfilRepository.findByTipo(tipo)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado: " + tipo));
    }
}