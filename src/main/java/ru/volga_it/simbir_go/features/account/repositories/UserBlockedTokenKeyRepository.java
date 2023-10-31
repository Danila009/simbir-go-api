package ru.volga_it.simbir_go.features.account.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.volga_it.simbir_go.features.account.entities.UserBlockedTokenKeyEntity;

import java.util.UUID;

public interface UserBlockedTokenKeyRepository extends JpaRepository<UserBlockedTokenKeyEntity, UUID> {}
