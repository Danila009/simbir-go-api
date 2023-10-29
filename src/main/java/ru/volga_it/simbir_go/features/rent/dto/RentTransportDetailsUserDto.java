package ru.volga_it.simbir_go.features.rent.dto;

import ru.volga_it.simbir_go.features.account.dto.UserShortDto;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportType;
import ru.volga_it.simbir_go.features.transport.dto.TransportDto;

public record RentTransportDetailsUserDto(
        Long id,
        Double finalPrice,
        Double priceOfUnit,
        RentTransportType type,
        UserShortDto user,
        TransportDto transport
) {}
