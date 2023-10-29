package ru.volga_it.simbir_go.features.rent;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.volga_it.simbir_go.features.account.services.security.UserSecurityService;
import ru.volga_it.simbir_go.features.rent.dto.RentTransportDetailsUserDto;
import ru.volga_it.simbir_go.features.rent.dto.RentTransportDto;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportType;
import ru.volga_it.simbir_go.features.rent.mappers.RentTransportDetailsUserMapper;
import ru.volga_it.simbir_go.features.rent.mappers.RentTransportMapper;
import ru.volga_it.simbir_go.features.rent.services.RentTransportService;
import ru.volga_it.simbir_go.features.transport.dto.GetTransportsRequestParams;
import ru.volga_it.simbir_go.features.transport.dto.TransportDto;
import ru.volga_it.simbir_go.features.transport.entities.TransportType;
import ru.volga_it.simbir_go.features.transport.mappers.TransportMapper;
import ru.volga_it.simbir_go.features.transport.services.TransportService;

import java.util.List;

@Valid
@RestController
@RequiredArgsConstructor
@Tag(name = "rent-controller")
@RequestMapping("/api/Rent/")
public class RentTransportController {

    private final RentTransportService rentTransportService;
    private final UserSecurityService userSecurityService;
    private final TransportService transportService;

    private final RentTransportMapper rentTransportMapper;
    private final RentTransportDetailsUserMapper rentTransportDetailsUserMapper;
    private final TransportMapper transportMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private List<TransportDto> getAll(
            @RequestParam(name = "lat", required = false) Double latitude,
            @RequestParam(name = "long", required = false) Double longitude,
            @RequestParam(required = false) Double radius,
            @RequestParam(required = false) TransportType type
    ) {
        GetTransportsRequestParams params = new GetTransportsRequestParams();
        params.setType(type);
        params.setRadius(radius);
        params.setLatitude(latitude);
        params.setLongitude(longitude);
        params.setCanBeRented(true);

        return transportMapper.toDto(transportService.getAll(params));
    }

    @GetMapping("{rentId}")
    @ResponseStatus(HttpStatus.OK)
    private RentTransportDetailsUserDto getById(@PathVariable Long rentId) {
        return rentTransportDetailsUserMapper.toDto(rentTransportService.getById(rentId));
    }

    @GetMapping("MyHistory")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    private List<RentTransportDto> getUserHistory() {
        Long userId = userSecurityService.getUserIdByAuthentication();
        return rentTransportMapper.toDto(rentTransportService.getAll(null, userId));
    }

    @GetMapping("TransportHistory/{transportId}")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    private List<RentTransportDetailsUserDto> getTransportHistory(@PathVariable Long transportId) {
        return rentTransportDetailsUserMapper.toDto(rentTransportService.getAll(transportId, null));
    }


    @PostMapping("New/{transportId}")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    private RentTransportDto userNew(
            @PathVariable Long transportId,
            RentTransportType type
    ) {
        Long userId = userSecurityService.getUserIdByAuthentication();
        return rentTransportMapper.toDto(rentTransportService.userNew(type, userId, transportId));
    }

    @PostMapping("End/{rentId}")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    private RentTransportDto userEnd(
            @PathVariable Long rentId,
            @RequestParam(name = "lat") Double latitude,
            @RequestParam(name = "long") Double longitude
    ) {
        Long userId = userSecurityService.getUserIdByAuthentication();
        return rentTransportMapper.toDto(rentTransportService.userEnd(latitude, longitude, userId, rentId));
    }
}
