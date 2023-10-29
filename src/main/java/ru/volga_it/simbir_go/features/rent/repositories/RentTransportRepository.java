package ru.volga_it.simbir_go.features.rent.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportEntity;

public interface RentTransportRepository extends JpaRepository<RentTransportEntity, Long> {}
