package com.codagis.agischool.controller;

import com.codagis.agischool.dto.UsuarioCadastroDto;
import com.codagis.agischool.model.Usuario;
import com.codagis.agischool.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody UsuarioCadastroDto usuarioCadastroDto) {
        return new ResponseEntity<>(usuarioService.cadastrarUsuario(usuarioCadastroDto), HttpStatus.CREATED);
    }
}