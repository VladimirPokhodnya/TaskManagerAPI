package com.github.vladimirpokhodnya.authjwtapi.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity(UserRegisterDTO userRegisterDTO);
    UserDTO toDTO(UserEntity userEntity);
}

