package ru.volga_it.simbir_go.features.rent.services;

import ru.volga_it.simbir_go.features.rent.entities.RentTransportEntity;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportType;
import ru.volga_it.simbir_go.features.transport.entities.TransportType;

import java.util.List;

public interface RentTransportService {

    List<RentTransportEntity> getAll(
            Long transportId,
            Long userId
    );

    RentTransportEntity getById(Long id);

    RentTransportEntity userNew(RentTransportType type, Long userId, Long transportId);
    RentTransportEntity userEnd(Double latitude, Double longitude, Long userId, Long rentId);
}
