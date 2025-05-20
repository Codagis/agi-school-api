package com.codagis.agischool.dto;

import com.codagis.agischool.enums.TipoPerfil;
import com.codagis.agischool.model.Perfil;
import com.codagis.agischool.model.Usuario;
import com.codagis.agischool.service.PerfilService;
import jakarta.validation.constraints.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

public record UsuarioCadastroDto(
        @NotBlank String nome,
        @NotBlank String matricula,
        @NotBlank String senha,
        @NotBlank String email,
        Set<TipoPerfil> tiposPerfil
) {
    public Usuario toUsuario(PasswordEncoder passwordEncoder, PerfilService perfilService) {
        Usuario usuario = new Usuario();
        usuario.setNome(this.nome());
        usuario.setMatricula(this.matricula());
        usuario.setEmail(this.email());
        usuario.setSenha(passwordEncoder.encode(this.senha()));

        Set<Perfil> perfis = this.tiposPerfil().stream()
                .map(perfilService::buscarPorTipo)
                .collect(Collectors.toSet());

        if (perfis.isEmpty()) {
            perfis.add(perfilService.buscarPorTipo(TipoPerfil.ROLE_ALUNO));
        }

        usuario.setPerfis(perfis);
        return usuario;
    }
}