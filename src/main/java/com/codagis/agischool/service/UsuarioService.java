package com.codagis.agischool.service;

import com.codagis.agischool.dto.UsuarioCadastroDto;
import com.codagis.agischool.enums.TipoPerfil;
import com.codagis.agischool.exception.EmailJaCadastradoException;
import com.codagis.agischool.exception.MatriculaJaCadastradaException;
import com.codagis.agischool.model.Perfil;
import com.codagis.agischool.model.Usuario;
import com.codagis.agischool.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final PerfilService perfilService;

    @Transactional
    public Usuario cadastrarUsuario(UsuarioCadastroDto usuarioCadastroDto) {
        if (usuarioRepository.existsByMatricula(usuarioCadastroDto.matricula())) {
            throw new MatriculaJaCadastradaException("Matrícula já cadastrada");
        }

        if (usuarioRepository.existsByEmail(usuarioCadastroDto.email())) {
            throw new EmailJaCadastradoException("Email já cadastrado");
        }
        Usuario usuario = usuarioCadastroDto.toUsuario(passwordEncoder, perfilService);
        usuario.setSenha(passwordEncoder.encode(usuarioCadastroDto.senha()));
        return usuarioRepository.save(usuario);
    }
}