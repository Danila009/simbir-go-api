package ru.volga_it.simbir_go.features.account.dto.security;

import lombok.Data;

@Data
public class JwtResponseDto {

    private Long userId;
    private Boolean isAdmin;
    private String accessToken;
}
