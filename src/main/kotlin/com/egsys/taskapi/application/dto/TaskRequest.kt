package com.egsys.taskapi.application.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


data class TaskRequest(

    @field:NotBlank(message = "O título é obrigatório")
    @field:Size(max = 200, message = "O título deve ter no máximo 200 caracteres")
    val title: String,

    @field:Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres")
    val description: String? = null,

    val categoryId: Long,

    val dateTime: String
)
