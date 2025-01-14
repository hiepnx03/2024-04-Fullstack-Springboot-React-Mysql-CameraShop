package com.example.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "API Documentation",
                version = "1.0",
                description = "API documentation for the application",
                contact = @Contact(
                        name = "Your Name",
                        email = "your.email@example.com",
                        url = "https://yourwebsite.com"
                )
        ),
        security = @SecurityRequirement(name = "bearerAuth"),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local server"),
//                @Server(url = "https://api.yourdomain.com", description = "Production server")
        }
//        security = @SecurityRequirement(name = "oauth2_bearer"),
//        servers = {
//                @Server(url = "${server.servlet.context-path}",
//                        description = "Default Server URL")
//        })
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)

//@SecurityScheme(name = "oauth2_bearer", type = SecuritySchemeType.OAUTH2,
//        flows = @OAuthFlows(
//                authorizationCode = @OAuthFlow(
//                        authorizationUrl = "${springdoc.oauthflow.authorization-url}",
//                        tokenUrl = "${springdoc.oauthflow.token-url}",
//                        scopes = {@OAuthScope(name = "openid", description = "openid")
//                        })))

public class SwaggerConfig {
}
