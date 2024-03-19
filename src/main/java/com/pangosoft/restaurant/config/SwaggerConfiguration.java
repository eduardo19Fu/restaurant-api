package com.pangosoft.restaurant.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    /**
     * Swagger generated documentation configuration
     * @return The necessary configuration to generate swagger page documentation successfully
     * */
    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(new Info()
                .title("Sistema Restaurante")
                .description("API para sistemas de restaurante")
                .version("Beta 1.0.0"));
    }
}
