package ru.volga_it.simbir_go.features.transport.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.volga_it.simbir_go.features.transport.entities.TransportEntity;

public interface TransportRepository extends JpaRepository<TransportEntity, Long> {}
