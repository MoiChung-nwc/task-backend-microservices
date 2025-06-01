package com.chung.authservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "api_permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;
    private String apiPathPattern;
    private String httpMethod;
    private String role;
    private boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getApiPathPattern() {
        return apiPathPattern;
    }

    public void setApiPathPattern(String apiPathPattern) {
        this.apiPathPattern = apiPathPattern;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}