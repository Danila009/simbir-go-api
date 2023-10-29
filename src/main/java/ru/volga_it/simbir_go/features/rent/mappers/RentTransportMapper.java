package ru.volga_it.simbir_go.features.rent.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.volga_it.simbir_go.common.mapper.Mappable;
import ru.volga_it.simbir_go.features.rent.dto.RentTransportDto;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportEntity;
import ru.volga_it.simbir_go.features.transport.mappers.TransportMapper;

@Mapper(componentModel = "spring", uses = TransportMapper.class)
public interface RentTransportMapper extends Mappable<RentTransportEntity, RentTransportDto> {

    @Override
    @Mapping(source = "entity.transportType.type", target = "type")
    RentTransportDto toDto(RentTransportEntity entity);
}
