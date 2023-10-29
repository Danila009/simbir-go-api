package ru.volga_it.simbir_go.features.account.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.volga_it.simbir_go.features.account.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}
