package ru.volga_it.simbir_go.features.account.services.blockedTokenKey;

import ru.volga_it.simbir_go.features.account.entities.UserBlockedTokenKeyEntity;

public interface UserBlockedTokenKeyService {

    Boolean tokenIsBlocked(Long userId, String userTokenKey);

    void save(UserBlockedTokenKeyEntity entity);
}
