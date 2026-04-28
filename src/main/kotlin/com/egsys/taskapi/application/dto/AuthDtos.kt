package com.egsys.taskapi.application.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class RegisterRequest(
    @field:NotBlank(message = "O nome é obrigatório")
    @field:Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    val name: String,

    @field:NotBlank(message = "O e-mail é obrigatório")
    @field:Email(message = "Formato de e-mail inválido")
    val email: String,

    @field:NotBlank(message = "A senha é obrigatória")
    @field:Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    val password: String
)


data class LoginRequest(
    @field:NotBlank(message = "O e-mail é obrigatório")
    @field:Email(message = "Formato de e-mail inválido")
    val email: String,

    @field:NotBlank(message = "A senha é obrigatória")
    val password: String
)


data class AuthResponse(
    val token: String,
    val type: String = "Bearer",
    val email: String,
    val name: String,
    val role: String
)


data class UserProfileResponse(
    val email: String,
    val name: String,
    val role: String
)
