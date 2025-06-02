package com.proyecto.ventas.proyectoventasbazar.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestión de Productos y Ventas")
                        .version("1.0.0")
                        .description("Documentación de la API para gestionar productos y ventas.")
                        .termsOfService("http://tusitio.com/terminos")
                        .contact(new Contact()
                                .name("Martín López")
                                .url("https://www.linkedin.com/in/martin-lopez-8264132a8/")
                                .email("martinlopez45630@gmail.com"))
                        .license(new License()
                                .name("Licencia MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
