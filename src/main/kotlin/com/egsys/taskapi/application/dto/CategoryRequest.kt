package com.egsys.taskapi.application.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * DTO para criação e atualização de Categoria.
 */
data class CategoryRequest(
    @field:NotBlank(message = "A descrição da categoria é obrigatória")
    @field:Size(max = 100, message = "A descrição deve ter no máximo 100 caracteres")
    val description: String
)
