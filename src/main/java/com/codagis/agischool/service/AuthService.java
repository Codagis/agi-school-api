package com.codagis.agischool.service;

import com.codagis.agischool.dto.JwtResponse;
import com.codagis.agischool.dto.LoginDto;
import com.codagis.agischool.dto.RefreshTokenRequest;
import com.codagis.agischool.exception.AutenticacaoException;
import com.codagis.agischool.model.Usuario;
import com.codagis.agischool.repository.UsuarioRepository;
import com.codagis.agischool.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioRepository usuarioRepository;

    public JwtResponse autenticarUsuario(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.matricula(),
                        loginDto.senha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario usuario = usuarioRepository.findByMatricula(loginDto.matricula())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String jwt = jwtTokenProvider.generateToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        Set<String> perfis = usuario.getPerfis().stream()
                .map(perfil -> perfil.getTipo().name())
                .collect(Collectors.toSet());

        return new JwtResponse(
                jwt,
                refreshToken,
                usuario.getMatricula(),
                usuario.getNome(),
                usuario.getEmail(),
                perfis);
    }

    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        if (!jwtTokenProvider.validateToken(refreshTokenRequest.refreshToken())) {
            throw new AutenticacaoException("Refresh token inválido ou expirado");
        }

        String matricula = jwtTokenProvider.getUsernameFromToken(refreshTokenRequest.refreshToken());
        Usuario usuario = usuarioRepository.findByMatricula(matricula)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        usuario.getMatricula(),
                        null,
                        usuario.getAuthorities());

        String jwt = jwtTokenProvider.generateToken(authentication);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        Set<String> perfis = usuario.getPerfis().stream()
                .map(perfil -> perfil.getTipo().name())
                .collect(Collectors.toSet());

        return new JwtResponse(
                jwt,
                newRefreshToken,
                usuario.getMatricula(),
                usuario.getNome(),
                usuario.getEmail(),
                perfis);
    }
}