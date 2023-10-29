package ru.volga_it.simbir_go.features.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
        String username,
        @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
        String password,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Double balance,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Boolean isAdmin
) {}
