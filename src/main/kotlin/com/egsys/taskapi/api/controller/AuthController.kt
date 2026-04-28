package com.egsys.taskapi.api.controller

import com.egsys.taskapi.application.dto.AuthResponse
import com.egsys.taskapi.application.dto.LoginRequest
import com.egsys.taskapi.application.dto.RegisterRequest
import com.egsys.taskapi.application.dto.UserProfileResponse
import com.egsys.taskapi.application.service.AuthService
import com.egsys.taskapi.domain.model.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Registro, login e perfil do usuário autenticado")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    @Operation(summary = "Cadastra um novo usuário")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request))

    @PostMapping("/login")
    @Operation(summary = "Autentica o usuário e retorna o token JWT")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(authService.login(request))

    @GetMapping("/me")
    @Operation(
        summary = "Retorna os dados do usuário autenticado",
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun me(@AuthenticationPrincipal user: User): ResponseEntity<UserProfileResponse> =
        ResponseEntity.ok(
            UserProfileResponse(
                email = user.email,
                name = user.name,
                role = user.role.name
            )
        )
}
