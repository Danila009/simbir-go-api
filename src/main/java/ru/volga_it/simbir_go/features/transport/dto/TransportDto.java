package ru.volga_it.simbir_go.features.transport.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ru.volga_it.simbir_go.common.validation.OnCreate;
import ru.volga_it.simbir_go.common.validation.OnUpdate;
import ru.volga_it.simbir_go.features.transport.entities.TransportType;

public record TransportDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
        @NotNull(message = "identifier must be not null", groups = {OnCreate.class, OnUpdate.class})
        String identifier,
        @NotNull(message = "model must be not null", groups = {OnCreate.class, OnUpdate.class})
        String model,
        @NotNull(message = "color must be not null", groups = {OnCreate.class, OnUpdate.class})
        String color,
        String description,
        @NotNull(message = "latitude must be not null", groups = {OnCreate.class, OnUpdate.class})
        Double latitude,
        @NotNull(message = "longitude must be not null", groups = {OnCreate.class, OnUpdate.class})
        Double longitude,
        Double minutePrice,
        Double dayPrice,
        @NotNull(message = "canBeRented must be not null", groups = {OnCreate.class, OnUpdate.class})
        Boolean canBeRented,
        @NotNull(message = "type must be not null", groups = {OnCreate.class})
        TransportType type
) {}
