package com.springsercurity.springsecurity.dto.request;

import com.springsercurity.springsecurity.constants.MessageConstants;
import com.springsercurity.springsecurity.security.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = MessageConstants.FIRST_NAME_REQUIRED)
    private String firstName;

    @NotBlank(message = MessageConstants.LAST_NAME_REQUIRED)
    private String lastName;

    @NotBlank(message = MessageConstants.EMAIL_REQUIRED)
    @Email(message = MessageConstants.EMAIL_INVALID)
    private String email;

    @Size(min = 6)
    @NotBlank(message = MessageConstants.PASSWORD_REQUIRED)
    private String password;

    private Role role;

}
