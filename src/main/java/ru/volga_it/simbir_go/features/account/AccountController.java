package ru.volga_it.simbir_go.features.account;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.volga_it.simbir_go.common.validation.OnCreate;
import ru.volga_it.simbir_go.common.validation.OnUpdate;
import ru.volga_it.simbir_go.features.account.dto.UserDto;
import ru.volga_it.simbir_go.features.account.dto.params.UpdateUserParams;
import ru.volga_it.simbir_go.features.account.dto.security.JwtRequestDto;
import ru.volga_it.simbir_go.features.account.dto.security.JwtResponseDto;
import ru.volga_it.simbir_go.features.account.mappers.UserMapper;
import ru.volga_it.simbir_go.features.account.services.UserService;
import ru.volga_it.simbir_go.features.account.services.security.UserSecurityService;

@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Account/")
public class AccountController {

    private final UserService userService;
    private final UserSecurityService userSecurityService;
    private final UserMapper userMapper;

    @GetMapping("Me")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    private UserDto getById() {
        Long userId = userSecurityService.getUserIdByAuthentication();
        return userMapper.toDto(userService.getById(userId));
    }

    @PostMapping("SignIn")
    @ResponseStatus(HttpStatus.CREATED)
    private JwtResponseDto login(@Validated(OnCreate.class) @RequestBody JwtRequestDto dto) {
        return userSecurityService.login(dto);
    }

    @PostMapping("SignUp")
    @ResponseStatus(HttpStatus.CREATED)
    private JwtResponseDto registration(@Validated(OnCreate.class) @RequestBody JwtRequestDto dto) {
        return userSecurityService.registration(dto);
    }

    @PostMapping("SignOut")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    private void signOut() {
        userSecurityService.signOut();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    private void update(@Validated(OnUpdate.class) @RequestBody UpdateUserParams params) {
        Long userId = userSecurityService.getUserIdByAuthentication();
        userService.update(userId, params);
    }
}
