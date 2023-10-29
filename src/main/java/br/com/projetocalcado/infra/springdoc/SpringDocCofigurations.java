package br.com.projetocalcado.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocCofigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("Projeto calçados API")
                        .description("API Rest da aplicação Projeto calçados , contendo as funcionalidades de CRUD de produto,fornecedor,categoria, além de lançamento de nota e autenticação com spring securiy e token JWT")
                        .contact(new Contact()
                                .name(" do Desenvolvedor Alexandre")
                                .email("alepaulino21@gmail.com"))
                        .license(new License()
                                .name("Github do projeto")
                                .url("https://github.com/paulino21/projetocalcado.git")));

    }
}
