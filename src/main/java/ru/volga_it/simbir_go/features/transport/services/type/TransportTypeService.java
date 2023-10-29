package ru.volga_it.simbir_go.features.transport.services.type;

import ru.volga_it.simbir_go.features.transport.entities.TransportType;
import ru.volga_it.simbir_go.features.transport.entities.TransportTypeEntity;

public interface TransportTypeService {

    TransportTypeEntity getByType(TransportType type);
}
