package ru.volga_it.simbir_go.features.account.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import ru.volga_it.simbir_go.common.validation.OnCreate;
import ru.volga_it.simbir_go.common.validation.OnUpdate;

public record UserShortDto(
        Long id,
        @NotNull(message = "username must be not null", groups = {OnCreate.class, OnUpdate.class})
        @Length(max = 64, message = "username must be smaller than 64 symbols", groups = {OnCreate.class, OnUpdate.class})
        String username
) { }