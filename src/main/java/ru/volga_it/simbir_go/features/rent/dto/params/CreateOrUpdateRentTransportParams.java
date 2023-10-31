package ru.volga_it.simbir_go.features.rent.dto.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ru.volga_it.simbir_go.common.validation.OnCreate;
import ru.volga_it.simbir_go.common.validation.OnUpdate;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportType;

import java.time.LocalDateTime;

public record CreateOrUpdateRentTransportParams(
        @NotNull(message = "transportId must be not null", groups = {OnCreate.class, OnUpdate.class})
        Long transportId,
        @NotNull(message = "userId must be not null", groups = {OnCreate.class, OnUpdate.class})
        Long userId,
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @NotNull(message = "timeStart must be not null", groups = {OnCreate.class, OnUpdate.class})
        LocalDateTime timeStart,
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        LocalDateTime timeEnd,
        @NotNull(message = "priceOfUnit must be not null", groups = {OnCreate.class, OnUpdate.class})
        Double priceOfUnit,
        @JsonProperty("priceType")
        @NotNull(message = "priceType must be not null", groups = {OnCreate.class, OnUpdate.class})
        RentTransportType type,
        @Min(value = 0, message = "finalPrice must be greater than or equal to zero",  groups = {OnCreate.class, OnUpdate.class})
        Double finalPrice
) {}
