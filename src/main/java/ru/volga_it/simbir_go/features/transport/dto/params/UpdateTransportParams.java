package ru.volga_it.simbir_go.features.transport.dto.params;

import jakarta.validation.constraints.NotNull;
import ru.volga_it.simbir_go.common.validation.OnCreate;
import ru.volga_it.simbir_go.common.validation.OnUpdate;
import ru.volga_it.simbir_go.features.transport.dto.params.admin.AdminCreateOrUpdateTransportParams;

public record UpdateTransportParams(
        @NotNull(message = "canBeRented must be not null", groups = {OnCreate.class, OnUpdate.class}) Boolean canBeRented,
        @NotNull(message = "model must be not null", groups = {OnCreate.class, OnUpdate.class}) String model,
        @NotNull(message = "color must be not null", groups = {OnCreate.class, OnUpdate.class}) String color,
        @NotNull(message = "identifier must be not null", groups = {OnCreate.class, OnUpdate.class}) String identifier,
        String description,
        @NotNull(message = "latitude must be not null", groups = {OnCreate.class, OnUpdate.class}) Double latitude,
        @NotNull(message = "longitude must be not null", groups = {OnCreate.class, OnUpdate.class}) Double longitude,
        Double minutePrice, Double dayPrice
) {

    public AdminCreateOrUpdateTransportParams toAdminParams() {
        AdminCreateOrUpdateTransportParams params = new AdminCreateOrUpdateTransportParams();

        params.setCanBeRented(canBeRented);
        params.setModel(model);
        params.setColor(color);
        params.setIdentifier(identifier);
        params.setDescription(description);
        params.setLatitude(latitude);
        params.setLongitude(longitude);
        params.setMinutePrice(minutePrice);
        params.setDayPrice(dayPrice);

        return params;
    }
}
