package com.juandavyc.product;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@OpenAPIDefinition(
        info = @Info(
                title = "Product Microservice REST API Documentation",
                description = "Product management microservice with JSON API standard",
                version = "v1.0.0",
                contact = @Contact(
                        name = "Juan Yara",
                        email = "juanda.yaracifuentes@gmail.com",
                        url = "https://juandavyc.dev"
                )
        )
)
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}
