package ru.volga_it.simbir_go.features.transport.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;

@Data
@Entity(name = "transport_types")
public class TransportTypeEntity {

    @Id
    private Short id;
    @Enumerated(EnumType.STRING)
    public TransportType type;

    @OneToMany(mappedBy = "typeEntity", fetch = FetchType.LAZY)
    private Collection<TransportEntity> transports;
}
