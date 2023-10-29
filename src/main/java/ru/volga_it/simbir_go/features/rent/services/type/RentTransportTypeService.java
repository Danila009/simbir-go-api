package ru.volga_it.simbir_go.features.rent.services.type;

import ru.volga_it.simbir_go.features.rent.entities.RentTransportType;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportTypeEntity;

public interface RentTransportTypeService {

    RentTransportTypeEntity getByType(RentTransportType type);
}
