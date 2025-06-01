package com.springsercurity.springsecurity.dto.request;

import com.springsercurity.springsecurity.security.Role;
import lombok.Data;

@Data
public class UpdateUserRoleRequest {

    private Role role;
}
