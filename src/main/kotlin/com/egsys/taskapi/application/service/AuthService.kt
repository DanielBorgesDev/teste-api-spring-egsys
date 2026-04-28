package com.egsys.taskapi.application.service

import com.egsys.taskapi.application.dto.AuthResponse
import com.egsys.taskapi.application.dto.LoginRequest
import com.egsys.taskapi.application.dto.RegisterRequest
import com.egsys.taskapi.application.exception.ResourceAlreadyExistsException
import com.egsys.taskapi.domain.model.Role
import com.egsys.taskapi.domain.model.User
import com.egsys.taskapi.domain.repository.UserRepository
import com.egsys.taskapi.infrastructure.security.JwtService
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {

    private val log = LoggerFactory.getLogger(AuthService::class.java)

    fun register(request: RegisterRequest): AuthResponse {
        log.debug("Registrando usuário: ${request.email}")
        if (userRepository.existsByEmail(request.email)) {
            throw ResourceAlreadyExistsException("E-mail já cadastrado: ${request.email}")
        }

        val user = User(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = Role.USER
        )

        val saved = userRepository.save(user)
        val token = jwtService.generateToken(saved)
        log.debug("Usuário registrado com sucesso: ${saved.email}")

        return AuthResponse(
            token = token,
            email = saved.email,
            name = saved.name,
            role = saved.role.name
        )
    }

    fun login(request: LoginRequest): AuthResponse {
        log.debug("Autenticando usuário: ${request.email}")
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password)
        )

        val user = userRepository.findByEmail(request.email)
            .orElseThrow { IllegalStateException("Usuário não encontrado após autenticação") }

        val token = jwtService.generateToken(user)
        log.debug("Login bem-sucedido: ${user.email}")

        return AuthResponse(
            token = token,
            email = user.email,
            name = user.name,
            role = user.role.name
        )
    }
}
