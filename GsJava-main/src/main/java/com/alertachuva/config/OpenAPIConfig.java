package com.alertachuva.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI alertaChuvaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AlertaChuva API")
                        .description("Sistema de alerta para chuvas fortes e enchentes")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Equipe FIAP")
                                .email("contato@alertachuva.com.br")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub do Projeto")
                        .url("https://github.com/seu-repo/alertachuva-api"));
    }
}
