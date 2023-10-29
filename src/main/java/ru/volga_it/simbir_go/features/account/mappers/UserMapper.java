package ru.volga_it.simbir_go.features.account.mappers;

import org.mapstruct.Mapper;
import ru.volga_it.simbir_go.common.mapper.Mappable;
import ru.volga_it.simbir_go.features.account.dto.UserDto;
import ru.volga_it.simbir_go.features.account.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<UserEntity, UserDto> {}
