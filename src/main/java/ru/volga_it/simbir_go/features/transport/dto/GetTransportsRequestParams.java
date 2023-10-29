package ru.volga_it.simbir_go.features.transport.dto;

import lombok.Data;
import ru.volga_it.simbir_go.features.transport.entities.TransportType;

@Data
public class GetTransportsRequestParams {

    private Double latitude = null;
    private Double longitude = null;
    private Double radius = null;
    private TransportType type = null;
    private Boolean canBeRented = null;
}
