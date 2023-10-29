package ru.volga_it.simbir_go.features.transport.services;

import org.springframework.data.domain.Page;
import ru.volga_it.simbir_go.features.transport.dto.params.GetTransportsRequestParams;
import ru.volga_it.simbir_go.features.transport.dto.params.TransportTypeRequestParam;
import ru.volga_it.simbir_go.features.transport.dto.params.UpdateTransportParams;
import ru.volga_it.simbir_go.features.transport.dto.params.admin.AdminCreateOrUpdateTransportParams;
import ru.volga_it.simbir_go.features.transport.entities.TransportEntity;

import java.util.List;

public interface TransportService {

    TransportEntity getById(Long id);

    List<TransportEntity> getAll(GetTransportsRequestParams params);

    Page<TransportEntity> getAll(Integer offset, Integer limit, TransportTypeRequestParam type);

    TransportEntity add(Long ownerId, TransportEntity transport);

    void update(Long id, UpdateTransportParams params);
    void update(Long id, AdminCreateOrUpdateTransportParams params);

    void deletedById(Long id);
}
