package ru.volga_it.simbir_go.features.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import ru.volga_it.simbir_go.common.validation.OnCreate;
import ru.volga_it.simbir_go.common.validation.OnUpdate;

public record UserDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
        @NotNull(message = "username must be not null", groups = {OnCreate.class, OnUpdate.class})
        @Length(max = 64, message = "username must be smaller than 30 symbols", groups = {OnCreate.class, OnUpdate.class})
        String username,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Double balance,
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Boolean isAdmin
) {}
