package tn.esprit.ressourcesservice.Confg;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class config {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("My Spring Boot API")
                        .description("API documentation with Swagger UI")
                        .version("1.0.0"));
    }
}
