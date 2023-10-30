package ru.volga_it.simbir_go.features.transport.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import ru.volga_it.simbir_go.common.validation.OnCreate;
import ru.volga_it.simbir_go.common.validation.OnUpdate;
import ru.volga_it.simbir_go.features.transport.entities.TransportType;

public record TransportDto(
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
        @Length(max = 64, message = "identifier must be smaller than 30 symbols", groups = {OnCreate.class, OnUpdate.class})
        @NotNull(message = "identifier must be not null", groups = {OnCreate.class, OnUpdate.class})
        String identifier,
        @Length(max = 64, message = "model must be smaller than 30 symbols", groups = {OnCreate.class, OnUpdate.class})
        @NotNull(message = "model must be not null", groups = {OnCreate.class, OnUpdate.class})
        String model,
        @Length(max = 48, message = "color must be smaller than 30 symbols", groups = {OnCreate.class, OnUpdate.class})
        @NotNull(message = "color must be not null", groups = {OnCreate.class, OnUpdate.class})
        String color,
        @Length(max = 256, message = "description must be smaller than 30 symbols", groups = {OnCreate.class, OnUpdate.class})
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
