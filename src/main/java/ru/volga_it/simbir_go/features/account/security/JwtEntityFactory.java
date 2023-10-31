package ru.volga_it.simbir_go.features.account.security;

import ru.volga_it.simbir_go.features.account.entities.UserEntity;

public final class JwtEntityFactory {

    public static JwtEntity create(final String tokenKey, final UserEntity user) {
        return new JwtEntity(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getIsAdmin(),
                tokenKey
        );
    }
}
