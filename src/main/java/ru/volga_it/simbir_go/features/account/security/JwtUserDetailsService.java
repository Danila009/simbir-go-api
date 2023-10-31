package ru.volga_it.simbir_go.features.account.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.volga_it.simbir_go.features.account.entities.UserEntity;
import ru.volga_it.simbir_go.features.account.services.UserService;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService {

    private final UserService userService;

    public UserDetails getUserDetailsByUsername(final String tokenKey, String username) throws UsernameNotFoundException {
        UserEntity user = userService.getByUsername(username);
        return JwtEntityFactory.create(tokenKey, user);
    }

}
