package ru.volga_it.simbir_go.features.transport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.volga_it.simbir_go.features.transport.entities.TransportType;
import ru.volga_it.simbir_go.features.transport.entities.TransportTypeEntity;

import java.util.Optional;

public interface TransportTypeRepository extends JpaRepository<TransportTypeEntity, Short> {

    Optional<TransportTypeEntity> findByType(TransportType type);
}
