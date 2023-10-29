package ru.volga_it.simbir_go.features.account.dto.admin;

public record AdminCreateOrUpdateUserRequestBody(
        String username,
        String password,
        Boolean isAdmin,
        Double balance
) {}
