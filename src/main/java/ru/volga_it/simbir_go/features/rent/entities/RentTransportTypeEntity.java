package ru.volga_it.simbir_go.features.rent.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
@Entity(name = "rent_transport_types")
public class RentTransportTypeEntity {

    @Id
    private Short id;

    @Enumerated(EnumType.STRING)
    private RentTransportType type;

    @OneToMany(mappedBy = "transportType", fetch = FetchType.LAZY)
    private Collection<RentTransportEntity> rentTransports;
}
