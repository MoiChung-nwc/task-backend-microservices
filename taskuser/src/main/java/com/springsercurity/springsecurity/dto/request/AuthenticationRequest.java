package com.springsercurity.springsecurity.dto.request;

import com.springsercurity.springsecurity.constants.MessageConstants;
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
public class AuthenticationRequest {

    @Email
    @NotBlank(message = MessageConstants.EMAIL_REQUIRED)
    private String email;

    @NotBlank(message = MessageConstants.PASSWORD_REQUIRED)
    @Size(min = 6)
    private String password;

}