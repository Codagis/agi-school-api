package com.codagis.agischool.dto;

import java.util.Set;

public record JwtResponse(
    String token,
    String refreshToken,
    String matricula,
    String nome,
    String email,
    Set<String> perfis
) {}