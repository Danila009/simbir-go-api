package ru.volga_it.simbir_go.features.account.services.blockedTokenKey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.volga_it.simbir_go.features.account.entities.UserBlockedTokenKeyEntity;
import ru.volga_it.simbir_go.features.account.repositories.UserBlockedTokenKeyRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserBlockedTokenKeyServiceImpl implements UserBlockedTokenKeyService {

    private final UserBlockedTokenKeyRepository userBlockedTokenKeyRepository;

    @Override
    @Transactional(readOnly = true)
    public Boolean tokenIsBlocked(Long userId, String userTokenKey) {
        Optional<UserBlockedTokenKeyEntity> userBlockedTokenKey =
                userBlockedTokenKeyRepository.findById(UUID.fromString(userTokenKey));

        return userBlockedTokenKey.isPresent() && Objects.equals(userBlockedTokenKey.get().getUser().getId(), userId);
    }

    @Override
    @Transactional
    public void save(UserBlockedTokenKeyEntity entity) {
        userBlockedTokenKeyRepository.save(entity);
    }
}
