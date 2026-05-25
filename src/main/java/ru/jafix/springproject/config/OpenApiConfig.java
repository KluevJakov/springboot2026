package ru.jafix.springproject.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
       return new OpenAPI()
               .info(new Info()
                       .title("API пет-проекта для управления задачами")
                       .version("1.0")
                       .description("Проект геймификации повседневных задач")
                       .contact(new Contact()
                               .email("kluevja@gmail.com")
                               .name("Kluev J.A."))
                       .license(new License()
                               .name("Все права принаддежат разработчику...")
                               .identifier("234565432623"))
               );
    }
}
