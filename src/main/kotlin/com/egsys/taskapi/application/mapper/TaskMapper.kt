package com.egsys.taskapi.application.mapper

import com.egsys.taskapi.application.dto.CategoryResponse
import com.egsys.taskapi.application.dto.TaskResponse
import com.egsys.taskapi.domain.model.Category
import com.egsys.taskapi.domain.model.Task
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

/**
 * Responsável por converter entidades de domínio em DTOs de resposta.
 * Centraliza a lógica de mapeamento, mantendo as camadas desacopladas.
 */
@Component
class TaskMapper {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    fun toResponse(category: Category): CategoryResponse =
        CategoryResponse(
            id = category.id,
            description = category.description
        )

    fun toResponse(task: Task): TaskResponse =
        TaskResponse(
            id = task.id,
            title = task.title,
            description = task.description,
            category = toResponse(task.category),
            dateTime = task.dateTime.format(formatter)
        )
}
