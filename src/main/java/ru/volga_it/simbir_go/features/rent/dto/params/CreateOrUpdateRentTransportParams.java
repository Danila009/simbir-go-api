package ru.volga_it.simbir_go.features.rent.dto.params;

import ru.volga_it.simbir_go.features.rent.entities.RentTransportType;

import java.time.LocalDateTime;

public record CreateOrUpdateRentTransportParams(
        Long transportId,
        Long userId,
        LocalDateTime timeStart,
        LocalDateTime timeEnd,
        Double priceOfUnit,
        RentTransportType type,
        Double finalPrice
) {}
