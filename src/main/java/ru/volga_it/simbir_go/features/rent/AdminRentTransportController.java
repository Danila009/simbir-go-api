package ru.volga_it.simbir_go.features.rent;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.volga_it.simbir_go.common.validation.OnCreate;
import ru.volga_it.simbir_go.common.validation.OnUpdate;
import ru.volga_it.simbir_go.features.rent.dto.RentTransportDto;
import ru.volga_it.simbir_go.features.rent.dto.params.CreateOrUpdateRentTransportParams;
import ru.volga_it.simbir_go.features.rent.mappers.RentTransportMapper;
import ru.volga_it.simbir_go.features.rent.services.RentTransportService;

import java.util.List;

@Valid
@RestController
@RequiredArgsConstructor
@Tag(name = "admin-rent-controller")
@RequestMapping("/api/Admin/Rent/")
public class AdminRentTransportController {

    private final RentTransportService rentTransportService;

    private final RentTransportMapper rentTransportMapper;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    private RentTransportDto getById(@PathVariable Long id) {
        return rentTransportMapper.toDto(rentTransportService.getById(id));
    }

    @GetMapping("UserHistory/{userId}")
    @ResponseStatus(HttpStatus.OK)
    private List<RentTransportDto> getUserHistory(@PathVariable Long userId) {
        return rentTransportMapper.toDto(rentTransportService.getAll(null, userId));
    }

    @GetMapping("TransportHistory/{transportId}")
    @ResponseStatus(HttpStatus.OK)
    private List<RentTransportDto> getTransportHistory(@PathVariable Long transportId) {
        return rentTransportMapper.toDto(rentTransportService.getAll(transportId, null));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private RentTransportDto add(@Validated(OnCreate.class) @RequestBody CreateOrUpdateRentTransportParams params) {
        return rentTransportMapper.toDto(rentTransportService.add(params));
    }

    @PostMapping("End/{rentId}")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    private RentTransportDto userEnd(
            @PathVariable Long rentId,
            @RequestParam(name = "lat") Double latitude,
            @RequestParam(name = "long") Double longitude
    ) {
        return rentTransportMapper.toDto(rentTransportService.userEnd(latitude, longitude, rentId));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void update(
            @PathVariable Long id,
            @Validated(OnUpdate.class) @RequestBody CreateOrUpdateRentTransportParams params
    ) {
        rentTransportService.update(id, params);
    }

    @DeleteMapping("{rantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteById(@PathVariable Long rantId) {
        rentTransportService.deleteById(rantId);
    }
}
