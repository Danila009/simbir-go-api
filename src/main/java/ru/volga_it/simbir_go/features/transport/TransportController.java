package ru.volga_it.simbir_go.features.transport;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.volga_it.simbir_go.common.validation.OnCreate;
import ru.volga_it.simbir_go.common.validation.OnUpdate;
import ru.volga_it.simbir_go.features.account.services.security.UserSecurityService;
import ru.volga_it.simbir_go.features.transport.dto.TransportDto;
import ru.volga_it.simbir_go.features.transport.dto.params.UpdateTransportParams;
import ru.volga_it.simbir_go.features.transport.mappers.TransportMapper;
import ru.volga_it.simbir_go.features.transport.services.TransportService;

import java.util.List;

@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Transport/")
public class TransportController {

    private final TransportService transportService;
    private final UserSecurityService userSecurityService;

    private final TransportMapper transportMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private List<TransportDto> getAll() {
        return transportMapper.toDto(transportService.getAll(null));
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
    private void update(
            @PathVariable Long id,
            @Validated(OnUpdate.class) @RequestBody UpdateTransportParams params
    ) {
        transportService.update(id, params);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteById(@PathVariable Long id) {
        transportService.deletedById(id);
    }
}
