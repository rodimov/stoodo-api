package fr.stoodev.stoodo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(info =
    @Info(
            title = "Stoodo API",
            version = "0.1",
            description = "Debug version"
    )
)
public class StoodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoodoApplication.class, args);
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

}
