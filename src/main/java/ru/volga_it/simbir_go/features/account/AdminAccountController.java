package ru.volga_it.simbir_go.features.account;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.volga_it.simbir_go.common.dto.PageDto;
import ru.volga_it.simbir_go.common.exceptions.ForbiddenException;
import ru.volga_it.simbir_go.common.validation.OnCreate;
import ru.volga_it.simbir_go.common.validation.OnUpdate;
import ru.volga_it.simbir_go.features.account.dto.UserDto;
import ru.volga_it.simbir_go.features.account.dto.UserShortDto;
import ru.volga_it.simbir_go.features.account.dto.admin.AdminCreateOrUpdateUserRequestBody;
import ru.volga_it.simbir_go.features.account.dto.admin.AdminUpdateUserParams;
import ru.volga_it.simbir_go.features.account.entities.UserEntity;
import ru.volga_it.simbir_go.features.account.mappers.UserMapper;
import ru.volga_it.simbir_go.features.account.mappers.UserShortMapper;
import ru.volga_it.simbir_go.features.account.mappers.admin.AdminCreateOrUpdateUserRequestBodyMapper;
import ru.volga_it.simbir_go.features.account.services.UserService;
import ru.volga_it.simbir_go.common.security.expressions.CustomSecurityExpression;

@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Admin/Account")
public class AdminAccountController {

    private final UserService userService;

    private final CustomSecurityExpression customSecurityExpression;

    private final UserMapper userMapper;
    private final UserShortMapper userShortMapper;
    private final AdminCreateOrUpdateUserRequestBodyMapper adminCreateOrUpdateUserRequestBodyMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    private PageDto<UserDto> getAll(
            @RequestParam(name = "start", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(name = "count", defaultValue = "20") @Min(1) @Max(100) Integer limit
    ) {
        if(!customSecurityExpression.hasIsAdmin()) throw new ForbiddenException("user is not an admin");

        Page<UserDto> page =  userService.getAll(offset, limit).map(userMapper::toDto);
        return new PageDto<>(page);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    private UserDto getById(@PathVariable Long id) {
        if(!customSecurityExpression.hasIsAdmin()) throw new ForbiddenException("user is not an admin");

        return userMapper.toDto(userService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    private UserShortDto add(@Validated(OnCreate.class) @RequestBody AdminCreateOrUpdateUserRequestBody body) {
        if(!customSecurityExpression.hasIsAdmin()) throw new ForbiddenException("user is not an admin");

        UserEntity user = adminCreateOrUpdateUserRequestBodyMapper.toEntity(body);
        return userShortMapper.toDto(userService.add(user));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    private void update(
            @PathVariable Long id,
            @Validated(OnUpdate.class) @RequestBody AdminUpdateUserParams params
    ) {
        if(!customSecurityExpression.hasIsAdmin()) throw new ForbiddenException("user is not an admin");

        userService.update(id, params);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    private void deleteById(@PathVariable Long id) {
        if(!customSecurityExpression.hasIsAdmin()) throw new ForbiddenException("user is not an admin");

        userService.deleteById(id);
    }
}
