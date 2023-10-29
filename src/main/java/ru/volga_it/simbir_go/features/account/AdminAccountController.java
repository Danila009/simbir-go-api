package ru.volga_it.simbir_go.features.account;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.volga_it.simbir_go.common.dto.PageDto;
import ru.volga_it.simbir_go.common.validation.OnCreate;
import ru.volga_it.simbir_go.common.validation.OnUpdate;
import ru.volga_it.simbir_go.features.account.dto.UserDto;
import ru.volga_it.simbir_go.features.account.dto.UserShortDto;
import ru.volga_it.simbir_go.features.account.dto.admin.AdminCreateOrUpdateUserRequestBody;
import ru.volga_it.simbir_go.features.account.entities.UserEntity;
import ru.volga_it.simbir_go.features.account.mappers.UserMapper;
import ru.volga_it.simbir_go.features.account.mappers.UserShortMapper;
import ru.volga_it.simbir_go.features.account.mappers.admin.AdminCreateOrUpdateUserRequestBodyMapper;
import ru.volga_it.simbir_go.features.account.services.UserService;

@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Admin/Account")
public class AdminAccountController {

    private final UserService userService;

    private final UserMapper userMapper;
    private final UserShortMapper userShortMapper;
    private final AdminCreateOrUpdateUserRequestBodyMapper adminCreateOrUpdateUserRequestBodyMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private PageDto<UserDto> getAll(
            @RequestParam(name = "start", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(name = "count", defaultValue = "20") @Min(1) @Max(100) Integer limit
    ) {
        Page<UserDto> page =  userService.getAll(offset, limit).map(userMapper::toDto);
        return new PageDto<>(page);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    private UserDto getById(@PathVariable Long id) {
        return userMapper.toDto(userService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private UserShortDto add(@Validated(OnCreate.class) @RequestBody AdminCreateOrUpdateUserRequestBody body) {
        UserEntity user = adminCreateOrUpdateUserRequestBodyMapper.toEntity(body);
        return userShortMapper.toDto(userService.add(user));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void update(
            @PathVariable Long id,
            @Validated(OnUpdate.class) @RequestBody AdminCreateOrUpdateUserRequestBody body
    ) {
        UserEntity user = adminCreateOrUpdateUserRequestBodyMapper.toEntity(body);
        userService.update(id, user);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
