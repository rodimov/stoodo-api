package fr.stoodev.stoodo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info =@Info(
                title = "Stoodo API",
                version = "1.0",
                contact = @Contact(
                        name = "Admin", email = "admin@stoodo.fr", url = "https://www.stoodo.fr"
                ),
                license = @License(
                        name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "https://www.stoodo.fr/tos",
                description = "API for stoodo app"
        ),
        servers = {
                @Server(
                        url = "http://188.166.114.84/api/v1",
                        description = "Demo"
                ),
                @Server(
                        url = "http://localhost:8080/api/v1",
                        description = "Develop"
                ),
        }
)
public class OpenAPIConfiguration {}
