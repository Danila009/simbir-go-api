package ru.volga_it.simbir_go.features.transport;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.volga_it.simbir_go.common.dto.PageDto;
import ru.volga_it.simbir_go.common.validation.OnUpdate;
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

    private final TransportMapper transportMapper;
    private final TransportDetailsMapper transportDetailsMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private PageDto<TransportDto> getAll(
            @RequestParam(name = "start", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(name = "count", defaultValue = "20") @Min(1) @Max(100) Integer limit,
            @RequestParam(defaultValue = "All") TransportTypeRequestParam type
    ) {
        Page<TransportDto> result = transportService.getAll(offset, limit, type).map(transportMapper::toDto);
        return new PageDto<>(result);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    private TransportDetailsDto getById(@PathVariable Long id) {
        return transportDetailsMapper.toDto(transportService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private TransportDto add(
            @Validated(OnUpdate.class) @RequestBody AdminCreateOrUpdateTransportParams params
    ) {
        TransportEntity transport = params.toEntity();
        return transportMapper.toDto(transportService.add(params.getOwnerId(), transport));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void update(
            @PathVariable Long id,
            @Validated(OnUpdate.class) @RequestBody AdminCreateOrUpdateTransportParams params
    ) {
        transportService.update(id, params);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteById(@PathVariable Long id) {
        transportService.deletedById(id);
    }
}
