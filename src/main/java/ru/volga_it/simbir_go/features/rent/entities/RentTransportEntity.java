package ru.volga_it.simbir_go.features.rent.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.volga_it.simbir_go.features.account.entities.UserEntity;
import ru.volga_it.simbir_go.features.transport.entities.TransportEntity;

import java.time.LocalDateTime;

@Data
@Entity(name = "rent_transports")
public class RentTransportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double finalPrice;
    private Double priceOfUnit;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_id")
    private TransportEntity transport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private RentTransportTypeEntity transportType;
}
