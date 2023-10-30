package ru.volga_it.simbir_go.features.transport;

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
import ru.volga_it.simbir_go.common.validation.OnUpdate;
import ru.volga_it.simbir_go.common.security.expressions.CustomSecurityExpression;
import ru.volga_it.simbir_go.features.transport.dto.TransportDetailsDto;
import ru.volga_it.simbir_go.features.transport.dto.TransportDto;
import ru.volga_it.simbir_go.features.transport.dto.params.TransportTypeRequestParam;
import ru.volga_it.simbir_go.features.transport.dto.params.admin.AdminCreateOrUpdateTransportParams;
import ru.volga_it.simbir_go.features.transport.entities.TransportEntity;
import ru.volga_it.simbir_go.features.transport.mappers.TransportDetailsMapper;
import ru.volga_it.simbir_go.features.transport.mappers.TransportMapper;
import ru.volga_it.simbir_go.features.transport.services.TransportService;

@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Admin/Transport/")
public class AdminTransportController {

    private final TransportService transportService;

    private final CustomSecurityExpression customSecurityExpression;

    private final TransportMapper transportMapper;
    private final TransportDetailsMapper transportDetailsMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    private PageDto<TransportDto> getAll(
            @RequestParam(name = "start", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(name = "count", defaultValue = "20") @Min(1) @Max(100) Integer limit,
            @RequestParam(defaultValue = "All") TransportTypeRequestParam type
    ) {
        if(!customSecurityExpression.hasIsAdmin()) throw new ForbiddenException("user is not an admin");

        Page<TransportDto> result = transportService.getAll(offset, limit, type).map(transportMapper::toDto);
        return new PageDto<>(result);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    private TransportDetailsDto getById(@PathVariable Long id) {
        if(!customSecurityExpression.hasIsAdmin()) throw new ForbiddenException("user is not an admin");

        return transportDetailsMapper.toDto(transportService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    private TransportDto add(
            @Validated(OnUpdate.class) @RequestBody AdminCreateOrUpdateTransportParams params
    ) {
        if(!customSecurityExpression.hasIsAdmin()) throw new ForbiddenException("user is not an admin");

        TransportEntity transport = params.toEntity();
        return transportMapper.toDto(transportService.add(params.getOwnerId(), transport));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    private void update(
            @PathVariable Long id,
            @Validated(OnUpdate.class) @RequestBody AdminCreateOrUpdateTransportParams params
    ) {
        if(!customSecurityExpression.hasIsAdmin()) throw new ForbiddenException("user is not an admin");

        transportService.update(id, params);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    private void deleteById(@PathVariable Long id) {
        if(!customSecurityExpression.hasIsAdmin()) throw new ForbiddenException("user is not an admin");

        transportService.deletedById(id);
    }
}
