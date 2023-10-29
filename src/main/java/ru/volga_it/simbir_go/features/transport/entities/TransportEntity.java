package ru.volga_it.simbir_go.features.transport.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.volga_it.simbir_go.features.account.entities.UserEntity;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportEntity;

import java.util.Collection;

@Data
@Entity(name = "transports")
public class TransportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String identifier;
    private String model;
    private String color;
    private String description;
    private Double latitude;
    private Double longitude;
    @Column(name = "minute_price")
    private Double minutePrice;
    @Column(name = "day_price")
    private Double dayPrice;
    @Column(name = "can_be_rented")
    private Boolean canBeRented;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private TransportTypeEntity typeEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    @OneToMany(mappedBy = "transport", fetch = FetchType.LAZY)
    private Collection<RentTransportEntity> rentTransports;
}
