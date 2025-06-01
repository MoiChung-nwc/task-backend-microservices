package com.springsercurity.springsecurity.dto;

import com.springsercurity.springsecurity.security.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private List<TokenDTO> tokens;
}
