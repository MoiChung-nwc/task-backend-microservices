package com.chung.authservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckPermissionRequest {
    private String serviceName;
    private String apiPath;
    private String httpMethod;
    private String role;
}
