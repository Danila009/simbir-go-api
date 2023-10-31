package ru.volga_it.simbir_go.features.transport.dto.params;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import ru.volga_it.simbir_go.common.validation.OnCreate;
import ru.volga_it.simbir_go.common.validation.OnUpdate;
import ru.volga_it.simbir_go.features.transport.dto.params.admin.AdminCreateOrUpdateTransportParams;

public record UpdateTransportParams(
        @NotNull(message = "canBeRented must be not null", groups = {OnCreate.class, OnUpdate.class})
        Boolean canBeRented,
        @Length(max = 64, message = "model must be smaller than 64 symbols", groups = {OnCreate.class, OnUpdate.class})
        @NotNull(message = "model must be not null", groups = {OnCreate.class, OnUpdate.class})
        String model,
        @Length(max = 48, message = "color must be smaller than 48 symbols", groups = {OnCreate.class, OnUpdate.class})
        @NotNull(message = "color must be not null", groups = {OnCreate.class, OnUpdate.class})
        String color,
        @Length(max = 64, message = "identifier must be smaller than 64 symbols", groups = {OnCreate.class, OnUpdate.class})
        @NotNull(message = "identifier must be not null", groups = {OnCreate.class, OnUpdate.class})
        String identifier,
        @Length(max = 256, message = "description must be smaller than 256 symbols", groups = {OnCreate.class, OnUpdate.class})
        String description,
        @NotNull(message = "latitude must be not null", groups = {OnCreate.class, OnUpdate.class})
        Double latitude,
        @NotNull(message = "longitude must be not null", groups = {OnCreate.class, OnUpdate.class})
        Double longitude,
        @Min(value = 0, message = "minutePrice must be greater than or equal to zero",  groups = {OnCreate.class, OnUpdate.class})
        Double minutePrice,
        @Min(value = 0, message = "dayPrice must be greater than or equal to zero",  groups = {OnCreate.class, OnUpdate.class})
        Double dayPrice
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
