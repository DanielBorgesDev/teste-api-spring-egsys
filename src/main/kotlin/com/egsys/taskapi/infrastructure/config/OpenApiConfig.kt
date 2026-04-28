package com.egsys.taskapi.infrastructure.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.context.annotation.Configuration

/**
 * Configura a documentação OpenAPI (Swagger UI) com suporte a autenticação JWT.
 * Acesse: http://localhost:8080/swagger-ui.html
 */
@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "Task API - EGSys",
        version = "1.0",
        description = "API REST para gerenciamento de tarefas com autenticação JWT. " +
                "Faça login em /api/auth/login, copie o token e clique em 'Authorize' para testar os endpoints protegidos.",
        contact = Contact(name = "Daniel Borges", url = "https://github.com/DanielBorgesDev")
    )
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    `in` = SecuritySchemeIn.HEADER,
    description = "Insira o token JWT obtido em POST /api/auth/login"
)
class OpenApiConfig
