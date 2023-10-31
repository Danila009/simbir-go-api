package ru.volga_it.simbir_go.features.account.services.security;

import ru.volga_it.simbir_go.features.account.dto.security.JwtRequestDto;
import ru.volga_it.simbir_go.features.account.dto.security.JwtResponseDto;
import ru.volga_it.simbir_go.features.account.security.JwtEntity;

public interface UserSecurityService {

    JwtResponseDto login(JwtRequestDto dto);

    JwtResponseDto registration(JwtRequestDto dto);

    void signOut();

    JwtEntity getUserByAuthentication();

    Long getUserIdByAuthentication();
}
