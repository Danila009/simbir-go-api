package ru.volga_it.simbir_go.features.account.dto.security;

public record JwtRequestDto(
        String username,
        String password
) {}
