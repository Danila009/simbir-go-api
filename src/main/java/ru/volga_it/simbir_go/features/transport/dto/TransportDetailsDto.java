package ru.volga_it.simbir_go.features.transport.dto;

import ru.volga_it.simbir_go.features.account.dto.UserShortDto;
import ru.volga_it.simbir_go.features.transport.entities.TransportType;

public record TransportDetailsDto(
        Long id,
        String identifier,
        String model,
        String color,
        String description,
        Double latitude,
        Double longitude,
        Double minutePrice,
        Double dayPrice,
        Boolean canBeRented,
        TransportType type,
        UserShortDto owner
) {}
