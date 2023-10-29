package ru.volga_it.simbir_go.features.rent.services;

import ru.volga_it.simbir_go.features.rent.dto.params.CreateOrUpdateRentTransportParams;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportEntity;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportType;

import java.util.List;

public interface RentTransportService {

    List<RentTransportEntity> getAll(
            Long transportId,
            Long userId
    );

    RentTransportEntity getById(Long id);

    RentTransportEntity add(CreateOrUpdateRentTransportParams params);

    RentTransportEntity userNew(RentTransportType type, Long userId, Long transportId);
    RentTransportEntity userEnd(Double latitude, Double longitude, Long rentId);

    void update(Long id, CreateOrUpdateRentTransportParams params);

    void deleteById(Long id);
}
