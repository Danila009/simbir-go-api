package ru.volga_it.simbir_go.features.account.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportEntity;

import java.util.Collection;

@Data
@Entity(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private Double balance;
    @Column(name = "is_admin")
    private Boolean isAdmin;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Collection<RentTransportEntity> rentTransports;
}
