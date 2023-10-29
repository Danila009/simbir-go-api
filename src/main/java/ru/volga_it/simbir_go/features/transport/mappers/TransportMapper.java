package ru.volga_it.simbir_go.features.transport.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.volga_it.simbir_go.common.mapper.Mappable;
import ru.volga_it.simbir_go.features.transport.dto.TransportDto;
import ru.volga_it.simbir_go.features.transport.entities.TransportEntity;

@Mapper(componentModel = "spring")
public interface TransportMapper extends Mappable<TransportEntity, TransportDto> {

    @Override
    @Mapping(source = "entity.typeEntity.type", target = "type")
    TransportDto toDto(TransportEntity entity);

    @Override
    @Mapping(source = "dto.type", target = "typeEntity.type")
    TransportEntity toEntity(TransportDto dto);
}
