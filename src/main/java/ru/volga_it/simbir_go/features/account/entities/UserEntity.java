package ru.volga_it.simbir_go.features.account.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportEntity;
import ru.volga_it.simbir_go.features.transport.entities.TransportEntity;

import java.util.Collection;

@Data
@Entity(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(name = "password_hash")
    private String password;
    private Double balance;
    @Column(name = "is_admin")
    private Boolean isAdmin;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<RentTransportEntity> rentTransports;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<TransportEntity> ownerTransports;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<UserBlockedTokenKeyEntity> blockedTokenKeys;
}
