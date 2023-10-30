package ru.volga_it.simbir_go.common.security.expressions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.volga_it.simbir_go.features.account.security.JwtEntity;
import ru.volga_it.simbir_go.features.account.services.security.UserSecurityService;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportEntity;
import ru.volga_it.simbir_go.features.rent.services.RentTransportService;
import ru.volga_it.simbir_go.features.transport.entities.TransportEntity;
import ru.volga_it.simbir_go.features.transport.services.TransportService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final UserSecurityService userSecurityService;
    private final TransportService transportService;
    private final RentTransportService rentTransportService;

    public boolean canAccessTransport(final Long transportId) {
        Long userId = userSecurityService.getUserIdByAuthentication();
        TransportEntity transport = transportService.getById(transportId);

        return Objects.equals(transport.getOwner().getId(), userId);
    }

    public boolean canAccessRent(final Long rentId) {
        Long userId = userSecurityService.getUserIdByAuthentication();
        RentTransportEntity rent = rentTransportService.getById(rentId);

        return Objects.equals(rent.getTransport().getOwner().getId(), userId) ||
                Objects.equals(rent.getUser().getId(), userId);
    }

    public boolean canAccessCreateNewRent(Long transportId) {
        Long userId = userSecurityService.getUserIdByAuthentication();
        TransportEntity transport = transportService.getById(transportId);

        return !Objects.equals(transport.getOwner().getId(), userId);
    }

    public boolean canAccessUpdateRentToEnt(Long rentId) {
        Long userId = userSecurityService.getUserIdByAuthentication();
        RentTransportEntity rent = rentTransportService.getById(rentId);

        return Objects.equals(rent.getUser().getId(), userId);
    }

    public boolean canAccessPaymentHesoyam(Long accountId) {
        JwtEntity user = userSecurityService.getUserByAuthentication();
        return Objects.equals(user.getId(), accountId) || user.getIsAdmin();
    }

    public boolean hasIsAdmin() {
        JwtEntity user = userSecurityService.getUserByAuthentication();
        return user.getIsAdmin();
    }
}
