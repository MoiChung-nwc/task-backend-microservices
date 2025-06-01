package com.springsercurity.springsecurity.config;

import com.springsercurity.springsecurity.config.JwtAuthenticationFilter;
import com.springsercurity.springsecurity.service.impl.LogoutServiceimpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    public static final String AUTH_ENDPOINT = "/api/v1/auth/**";
    public static final String LOGOUT_ENDPOINT = "/api/v1/auth/logout";

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final LogoutServiceimpl logoutHandler;
    private final UserDetailsService userDetailsService;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Cho phép tất cả truy cập đến các endpoint auth và swagger
                        .requestMatchers(
                                AUTH_ENDPOINT,                     // /api/v1/auth/**
                                "/v3/api-docs/**",                 // OpenAPI docs JSON
                                "/swagger-ui/**",                  // Swagger UI static resources
                                "/swagger-ui.html",                // Swagger UI main page (thường redirect)
                                "/swagger-ui/index.html"           // Swagger UI trang chính
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl(LOGOUT_ENDPOINT)
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) ->
                                response.setStatus(HttpServletResponse.SC_OK)
                        )
                );

        return http.build();
    }

}