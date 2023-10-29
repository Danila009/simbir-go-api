package ru.volga_it.simbir_go.features.account.services.security;

import ru.volga_it.simbir_go.features.account.dto.security.JwtRequestDto;
import ru.volga_it.simbir_go.features.account.dto.security.JwtResponseDto;

public interface UserSecurityService {

    JwtResponseDto login(JwtRequestDto dto);

    JwtResponseDto registration(JwtRequestDto dto);

    Long getUserIdByAuthentication();
}
