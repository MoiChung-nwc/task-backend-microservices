package com.springsercurity.springsecurity.constants;

public class SecurityConstants {

    public static final String[] SWAGGER_WHITELIST = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/favicon.ico"
    };

    public static final String[] AUTH_WHITELIST = {
            "/api/v1/auth/**"
    };

    public static final String LOGOUT_URL = "/api/v1/auth/logout";
}