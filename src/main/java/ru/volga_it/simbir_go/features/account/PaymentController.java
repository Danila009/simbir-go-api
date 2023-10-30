package ru.volga_it.simbir_go.features.account;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.volga_it.simbir_go.common.exceptions.UnauthorizedException;
import ru.volga_it.simbir_go.features.account.services.UserService;
import ru.volga_it.simbir_go.features.security.expressions.CustomSecurityExpression;

@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Payment/")
public class PaymentController {

    private final UserService userService;

    private final CustomSecurityExpression customSecurityExpression;

    @PostMapping("Hesoyam/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    @SecurityRequirement(name = "bearerAuth")
    private void hesoyam(@PathVariable(name = "accountId") Long userId) {
        if(!customSecurityExpression.canAccessPaymentHesoyam(userId))
            throw new UnauthorizedException("access denied");

        userService.plusBalance(userId, 250000.0);
    }
}
