package ru.volga_it.simbir_go.features.rent.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.volga_it.simbir_go.common.mapper.Mappable;
import ru.volga_it.simbir_go.features.account.mappers.UserShortMapper;
import ru.volga_it.simbir_go.features.rent.dto.RentTransportDetailsUserDto;
import ru.volga_it.simbir_go.features.rent.entities.RentTransportEntity;
import ru.volga_it.simbir_go.features.transport.mappers.TransportMapper;

@Mapper(componentModel = "spring", uses = {TransportMapper.class, UserShortMapper.class})
public interface RentTransportDetailsUserMapper extends Mappable<RentTransportEntity, RentTransportDetailsUserDto> {

    @Override
    @Mapping(source = "entity.transportType.type", target = "type")
    RentTransportDetailsUserDto toDto(RentTransportEntity entity);
}
