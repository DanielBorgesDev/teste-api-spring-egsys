package com.egsys.taskapi.api.controller

import com.egsys.taskapi.application.dto.AuthResponse
import com.egsys.taskapi.application.dto.LoginRequest
import com.egsys.taskapi.application.dto.RegisterRequest
import com.egsys.taskapi.application.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Expõe os endpoints públicos de autenticação.
 *
 * POST /api/auth/register — cadastro de novo usuário
 * POST /api/auth/login    — autenticação e geração de token JWT
 */
@RestController
@RequestMapping("/api/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/register")
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request))

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(authService.login(request))
}
