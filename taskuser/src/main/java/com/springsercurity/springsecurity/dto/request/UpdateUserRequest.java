package com.springsercurity.springsecurity.dto.request;

import com.springsercurity.springsecurity.security.Role;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
