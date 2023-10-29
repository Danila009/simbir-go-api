package ru.volga_it.simbir_go.features.transport.services;

import ru.volga_it.simbir_go.features.transport.dto.GetTransportsRequestParams;
import ru.volga_it.simbir_go.features.transport.entities.TransportEntity;

import java.util.List;

public interface TransportService {

    TransportEntity getById(Long id);

    List<TransportEntity> getAll(GetTransportsRequestParams params);

    Long add(TransportEntity transport);

    void update(Long id, TransportEntity transport);

    void deletedById(Long id);
}
