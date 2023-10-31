package ru.volga_it.simbir_go.features.transport;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.volga_it.simbir_go.common.exceptions.ForbiddenException;
import ru.volga_it.simbir_go.common.validation.OnCreate;
import ru.volga_it.simbir_go.common.validation.OnUpdate;
import ru.volga_it.simbir_go.features.account.services.security.UserSecurityService;
import ru.volga_it.simbir_go.common.security.expressions.CustomSecurityExpression;
import ru.volga_it.simbir_go.features.transport.dto.TransportDto;
import ru.volga_it.simbir_go.features.transport.dto.params.UpdateTransportParams;
import ru.volga_it.simbir_go.features.transport.mappers.TransportMapper;
import ru.volga_it.simbir_go.features.transport.services.TransportService;

@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Transport/")
public class TransportController {

    private final TransportService transportService;
    private final UserSecurityService userSecurityService;

    private final CustomSecurityExpression customSecurityExpression;

    private final TransportMapper transportMapper;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    private TransportDto getById(@PathVariable Long id) {
        return transportMapper.toDto(transportService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    private TransportDto add(@Validated(OnCreate.class) @RequestBody TransportDto transportDto) {
        Long userId = userSecurityService.getUserIdByAuthentication();
        return transportMapper.toDto(transportService.add(userId, transportMapper.toEntity(transportDto)));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    private void update(
            @PathVariable Long id,
            @Validated(OnUpdate.class) @RequestBody UpdateTransportParams params
    ) {
        if(!customSecurityExpression.canAccessTransport(id))
            throw new ForbiddenException("access denied");

        transportService.update(id, params);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    private void deleteById(@PathVariable Long id) {
        if(!customSecurityExpression.canAccessTransport(id))
            throw new ForbiddenException("access denied");

        transportService.deletedById(id);
    }
}
