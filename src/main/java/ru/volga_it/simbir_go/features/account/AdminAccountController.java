package ru.volga_it.simbir_go.features.account;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ru.volga_it.simbir_go.common.dto.PageDto;
import ru.volga_it.simbir_go.features.account.dto.UserDto;
import ru.volga_it.simbir_go.features.account.mappers.UserMapper;
import ru.volga_it.simbir_go.features.account.services.UserService;

@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Admin/Account")
public class AdminAccountController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    private PageDto<UserDto> getAll(
            @RequestParam(name = "start", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(name = "count", defaultValue = "20") @Min(1) @Max(100) Integer limit
    ) {
        Page<UserDto> page =  userService.getAll(offset, limit).map(userMapper::toDto);
        return new PageDto<>(page);
    }

    @DeleteMapping("{id}")
    private void deleteById(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
