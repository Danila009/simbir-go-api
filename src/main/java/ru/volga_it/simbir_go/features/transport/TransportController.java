package ru.volga_it.simbir_go.features.transport;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.volga_it.simbir_go.common.validation.OnCreate;
import ru.volga_it.simbir_go.common.validation.OnUpdate;
import ru.volga_it.simbir_go.features.transport.dto.TransportDto;
import ru.volga_it.simbir_go.features.transport.mappers.TransportMapper;
import ru.volga_it.simbir_go.features.transport.services.TransportService;

import java.util.List;

@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Transport/")
public class TransportController {

    private final TransportService transportService;
    private final TransportMapper transportMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private List<TransportDto> getAll() {
        return transportMapper.toDto(transportService.getAll(null));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Long add(@Validated(OnCreate.class) @RequestBody TransportDto transportDto) {
        return transportService.add(transportMapper.toEntity(transportDto));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void update(
            @PathVariable Long id,
            @Validated(OnUpdate.class) @RequestBody TransportDto transportDto
    ) {
        transportService.update(id, transportMapper.toEntity(transportDto));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteById(@PathVariable Long id) {
        transportService.deletedById(id);
    }
}
