package br.com.fiap.oceanguardian.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Ocean Guardian API")
                        .version("1.0")
                        .description("API para gerenciamento de usuários e eventos de limpeza de praias")
                        .contact(new Contact()
                                .name("Ocean Guardian")
                                .email("OceanGuardian@gmail.com")
                                .url("http://oceanGuardian.com.br"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }

}