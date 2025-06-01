package com.springsercurity.springsecurity.dto;

import com.springsercurity.springsecurity.security.TokenType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {
    private Integer id;
    private String token;
    private TokenType tokenType;
    private boolean revoked;
    private boolean expired;
}
