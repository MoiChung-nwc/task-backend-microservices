package com.springsercurity.springsecurity.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${open.api.title}") String title,
                                 @Value("${open.api.version}") String version,
                                 @Value("${open.api.description}") String description,
                                 @Value("${open.api.serverUrl}") String serverUrl,
                                 @Value("${open.api.serverName}") String serverName) {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .version(version)
                        .description(description)
                        .license(new License().name("API License").url("http://domain.vn/license")))
                .servers(List.of(new Server().url(serverUrl).description(serverName)))
                // Thêm cấu hình security scheme
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                // Áp dụng security cho tất cả các API (hoặc bạn có thể áp dụng riêng từng endpoint)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}