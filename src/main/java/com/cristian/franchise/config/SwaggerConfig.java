package com.cristian.franchise.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Franchise API",
        version = "1.0.0",
        description = "API para gestionar franquicias, sucursales y productos."
))
public class SwaggerConfig {
}