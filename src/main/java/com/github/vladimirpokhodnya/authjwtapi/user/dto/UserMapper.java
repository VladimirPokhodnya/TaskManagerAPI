package com.github.vladimirpokhodnya.authjwtapi.user.dto;

import com.github.vladimirpokhodnya.authjwtapi.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserEntity toEntity(UserRegisterDTO userRegisterDTO);
}

