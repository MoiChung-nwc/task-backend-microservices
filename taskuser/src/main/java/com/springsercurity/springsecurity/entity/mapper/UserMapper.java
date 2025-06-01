package com.springsercurity.springsecurity.entity.mapper;

import com.springsercurity.springsecurity.dto.UserDTO;
import com.springsercurity.springsecurity.entity.User;

import java.util.Collections;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .tokens(user.getTokens() != null
                        ? TokenMapper.toDTOList(user.getTokens())
                        : Collections.emptyList())
                .build();
    }

    public static User toEntity(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .role(dto.getRole())
                .build();
    }
}