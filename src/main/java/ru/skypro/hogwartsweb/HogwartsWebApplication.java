package ru.skypro.hogwartsweb;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class HogwartsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HogwartsWebApplication.class, args);
    }

}
