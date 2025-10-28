package com.winwintravel.authapi.dto.mapping;

import com.winwintravel.authapi.dto.UserDTO;
import com.winwintravel.authapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapping {
    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);
}
