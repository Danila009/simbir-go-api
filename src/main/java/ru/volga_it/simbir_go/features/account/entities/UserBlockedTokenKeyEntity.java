package ru.volga_it.simbir_go.features.account.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "user_blocked_token_keys")
public class UserBlockedTokenKeyEntity {

    @Id
    private UUID key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
