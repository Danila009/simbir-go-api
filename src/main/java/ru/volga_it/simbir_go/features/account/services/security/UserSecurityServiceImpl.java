package ru.volga_it.simbir_go.features.account.services.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.volga_it.simbir_go.features.security.JwtEntity;
import ru.volga_it.simbir_go.features.security.JwtTokenProvider;
import ru.volga_it.simbir_go.features.account.dto.security.JwtRequestDto;
import ru.volga_it.simbir_go.features.account.dto.security.JwtResponseDto;
import ru.volga_it.simbir_go.features.account.entities.UserEntity;
import ru.volga_it.simbir_go.features.account.services.UserService;

@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional(readOnly = true)
    public JwtResponseDto login(JwtRequestDto dto) {
        JwtResponseDto jwtResponse = new JwtResponseDto();

        UserEntity user = userService.getByUsername(dto.username());
        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getUsername(), user.getIsAdmin());
        jwtResponse.setUserId(user.getId());
        jwtResponse.setIsAdmin(user.getIsAdmin());
        jwtResponse.setAccessToken(accessToken);

        return jwtResponse;
    }

    @Override
    @Transactional
    public JwtResponseDto registration(JwtRequestDto dto) {
        UserEntity user = new UserEntity();
        JwtResponseDto jwtResponse = new JwtResponseDto();

        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setBalance(0.0);
        user.setIsAdmin(false);

        user = userService.add(user);

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getUsername(), user.getIsAdmin());
        jwtResponse.setUserId(user.getId());
        jwtResponse.setIsAdmin(user.getIsAdmin());
        jwtResponse.setAccessToken(accessToken);

        return jwtResponse;
    }

    @Override
    public JwtEntity getUserByAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  (JwtEntity) authentication.getPrincipal();
    }

    @Override
    public Long getUserIdByAuthentication() {
        return getUserByAuthentication().getId();
    }
}
