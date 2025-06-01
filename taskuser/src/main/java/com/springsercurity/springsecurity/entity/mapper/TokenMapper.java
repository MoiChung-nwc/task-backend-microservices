package com.springsercurity.springsecurity.entity.mapper;

import com.springsercurity.springsecurity.dto.TokenDTO;
import com.springsercurity.springsecurity.entity.Token;

import java.util.List;
import java.util.stream.Collectors;

public class TokenMapper {

    public static TokenDTO toDTO(Token token) {
        if (token == null) return null;

        return TokenDTO.builder()
                .id(token.getId())
                .token(token.getToken())
                .tokenType(token.getTokenType())
                .revoked(token.isRevoked())
                .expired(token.isExpired())
                .build();
    }

    public static List<TokenDTO> toDTOList(List<Token> tokens) {
        if (tokens == null) return null;
        return tokens.stream()
                .map(TokenMapper::toDTO)
                .collect(Collectors.toList());
    }
}
