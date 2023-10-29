package ru.volga_it.simbir_go.features.transport.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.volga_it.simbir_go.features.transport.entities.TransportEntity;
import ru.volga_it.simbir_go.features.transport.entities.TransportType;

public interface TransportRepository extends JpaRepository<TransportEntity, Long> {

    @Query("SELECT t FROM transports t WHERE t.typeEntity.type = ?1")
    Page<TransportEntity> findAllByType(TransportType type, Pageable pageable);
}
