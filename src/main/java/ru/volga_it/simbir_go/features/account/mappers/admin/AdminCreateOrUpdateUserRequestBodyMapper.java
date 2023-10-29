package ru.volga_it.simbir_go.features.account.mappers.admin;

import org.mapstruct.Mapper;
import ru.volga_it.simbir_go.common.mapper.Mappable;
import ru.volga_it.simbir_go.features.account.dto.admin.AdminCreateOrUpdateUserRequestBody;
import ru.volga_it.simbir_go.features.account.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface AdminCreateOrUpdateUserRequestBodyMapper extends Mappable<UserEntity, AdminCreateOrUpdateUserRequestBody> {}
