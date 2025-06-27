package com.rhsystem.api.rhsystemapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private final ApplicationConfig applicationConfig;

    public OpenApiConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        SecurityScheme securityScheme = new SecurityScheme()
                .name(securitySchemeName)
                .type(SecurityScheme.Type.HTTP) // O tipo de segurança é HTTP
                .scheme("bearer") // O esquema é 'bearer'
                .bearerFormat("JWT"); // O formato do token
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(securitySchemeName);

        return new OpenAPI()
                // Adiciona o esquema de segurança definido aos componentes globais da API
                .components(new Components().addSecuritySchemes(securitySchemeName, securityScheme))
                // Adiciona o requisito de segurança a TODAS as operações da API
                .addSecurityItem(securityRequirement)
                // Adiciona informações gerais sobre a API
                .info(new Info()
                              .title("API de Autenticação Demo")
                              .version(applicationConfig.getVersion())
                              .description("Exemplo de API com autenticação JWT e Spring Security."));
    }
}
