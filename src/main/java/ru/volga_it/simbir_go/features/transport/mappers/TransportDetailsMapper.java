package ru.volga_it.simbir_go.features.transport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.volga_it.simbir_go.common.mapper.Mappable;
import ru.volga_it.simbir_go.features.account.dto.UserShortDto;
import ru.volga_it.simbir_go.features.transport.dto.TransportDetailsDto;
import ru.volga_it.simbir_go.features.transport.entities.TransportEntity;

@Mapper(componentModel = "spring", uses = UserShortDto.class)
public interface TransportDetailsMapper extends Mappable<TransportEntity, TransportDetailsDto> {

    @Override
    @Mapping(source = "entity.typeEntity.type", target = "type")
    TransportDetailsDto toDto(TransportEntity entity);

    @Override
    @Mapping(source = "dto.type", target = "typeEntity.type")
    TransportEntity toEntity(TransportDetailsDto dto);
}
