package ru.volga_it.simbir_go.features.account;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.volga_it.simbir_go.features.account.services.UserService;

@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Payment/")
public class PaymentController {

    private final UserService userService;

    @PostMapping("Hesoyam/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    private void hesoyam(@PathVariable(name = "accountId") Long userId) {
        userService.plusBalance(userId, 250000.0);
    }
}
