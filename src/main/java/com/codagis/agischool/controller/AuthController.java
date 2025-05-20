package com.codagis.agischool.controller;

import com.codagis.agischool.dto.JwtResponse;
import com.codagis.agischool.dto.LoginDto;
import com.codagis.agischool.dto.RefreshTokenRequest;
import com.codagis.agischool.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> autenticarUsuario(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.autenticarUsuario(loginDto));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
}