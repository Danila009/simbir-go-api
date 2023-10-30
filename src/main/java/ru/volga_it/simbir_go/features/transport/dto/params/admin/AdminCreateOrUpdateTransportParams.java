package ru.volga_it.simbir_go.features.transport.dto.params.admin;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.volga_it.simbir_go.common.validation.OnCreate;
import ru.volga_it.simbir_go.common.validation.OnUpdate;
import ru.volga_it.simbir_go.features.transport.entities.TransportEntity;
import ru.volga_it.simbir_go.features.transport.entities.TransportType;
import ru.volga_it.simbir_go.features.transport.entities.TransportTypeEntity;

@Getter
@Setter
public class AdminCreateOrUpdateTransportParams {

    @NotNull(message = "ownerId must be not null", groups = OnCreate.class)
    private Long ownerId;

    @NotNull(message = "canBeRented must be not null", groups = {OnCreate.class, OnUpdate.class})
    private Boolean canBeRented;

    @NotNull(message = "type must be not null", groups = OnCreate.class)
    private TransportType type;

    @Length(max = 64, message = "model must be smaller than 30 symbols", groups = {OnCreate.class, OnUpdate.class})
    @NotNull(message = "model must be not null", groups = {OnCreate.class, OnUpdate.class})
    private String model;

    @Length(max = 48, message = "color must be smaller than 30 symbols", groups = {OnCreate.class, OnUpdate.class})
    @NotNull(message = "color must be not null", groups = {OnCreate.class, OnUpdate.class})
    private String color;

    @Length(max = 64, message = "identifier must be smaller than 30 symbols", groups = {OnCreate.class, OnUpdate.class})
    @NotNull(message = "identifier must be not null", groups = {OnCreate.class, OnUpdate.class})
    private String identifier;

    @Length(max = 256, message = "description must be smaller than 30 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    @NotNull(message = "latitude must be not null", groups = {OnCreate.class, OnUpdate.class})
    private Double latitude;

    @NotNull(message = "longitude must be not null", groups = {OnCreate.class, OnUpdate.class})
    private Double longitude;

    private Double minutePrice;

    private Double dayPrice;

    public TransportEntity toEntity() {
        TransportEntity transport = new TransportEntity();

        transport.setCanBeRented(canBeRented);
        transport.setModel(model);
        transport.setColor(color);
        transport.setIdentifier(identifier);
        transport.setDescription(description);
        transport.setLatitude(latitude);
        transport.setLongitude(longitude);
        transport.setMinutePrice(minutePrice);
        transport.setDayPrice(dayPrice);

        if(type != null) {
            TransportTypeEntity typeEntity = new TransportTypeEntity();
            typeEntity.setType(type);
            transport.setTypeEntity(typeEntity);
        }

        return transport;
    }
}
