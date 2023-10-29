package ru.volga_it.simbir_go.features.rent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportType;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportTypeEntity;

import java.util.Optional;

public interface RentTransportTypeRepository extends JpaRepository<RentTransportTypeEntity, Short> {

    Optional<RentTransportTypeEntity> findByType(RentTransportType type);
}
