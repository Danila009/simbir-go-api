package ru.volga_it.simbir_go.features.transport.dto.params;

import ru.volga_it.simbir_go.features.transport.entities.TransportType;

public enum TransportTypeRequestParam {
    Car, Bike, Scooter, All;

    public static TransportType parseTransportType(TransportTypeRequestParam param) throws Exception {
        if(param == TransportTypeRequestParam.Car) return TransportType.Car;
        if(param == TransportTypeRequestParam.Bike) return TransportType.Bike;
        if(param == TransportTypeRequestParam.Scooter) return TransportType.Scooter;

        throw new Exception("Transport type request param invalid");
    }
}
