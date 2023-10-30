package ru.volga_it.simbir_go.features.account.dto.admin;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import ru.volga_it.simbir_go.common.validation.OnCreate;
import ru.volga_it.simbir_go.common.validation.OnUpdate;

public record AdminCreateOrUpdateUserRequestBody(
        @NotNull(message = "username must be not null", groups = {OnCreate.class, OnUpdate.class})
        @Length(max = 64, message = "username must be smaller than 30 symbols", groups = {OnCreate.class, OnUpdate.class})
        String username,
        @NotNull(message = "password must be not null", groups = {OnCreate.class, OnUpdate.class})
        @Length(max = 64, message = "password must be smaller than 30 symbols", groups = {OnCreate.class, OnUpdate.class})
        String password,
        @NotNull(message = "isAdmin must be not null", groups = {OnCreate.class, OnUpdate.class})
        Boolean isAdmin,
        @NotNull(message = "balance must be not null", groups = {OnCreate.class, OnUpdate.class})
        Double balance
) {}
