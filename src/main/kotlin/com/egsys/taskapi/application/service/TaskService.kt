package com.egsys.taskapi.application.service

import com.egsys.taskapi.application.dto.TaskRequest
import com.egsys.taskapi.application.dto.TaskResponse
import com.egsys.taskapi.application.exception.InvalidRequestException
import com.egsys.taskapi.application.exception.ResourceNotFoundException
import com.egsys.taskapi.application.mapper.TaskMapper
import com.egsys.taskapi.domain.model.Task
import com.egsys.taskapi.domain.repository.TaskRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

/**
 * Contém a lógica de negócio para o CRUD de tarefas.
 */
@Service
@Transactional(readOnly = true)
class TaskService(
    private val taskRepository: TaskRepository,
    private val categoryService: CategoryService,
    private val mapper: TaskMapper
) {

    private val log = LoggerFactory.getLogger(TaskService::class.java)

    fun findAll(): List<TaskResponse> {
        log.debug("Buscando todas as tarefas")
        return taskRepository.findAllWithCategory().map { mapper.toResponse(it) }
    }

    fun findById(id: Long): TaskResponse {
        log.debug("Buscando tarefa id=$id")
        return mapper.toResponse(findTaskOrThrow(id))
    }

    fun findByCategoryId(categoryId: Long): List<TaskResponse> {
        log.debug("Buscando tarefas por categoria id=$categoryId")
        categoryService.findCategoryOrThrow(categoryId) // valida existência
        return taskRepository.findByCategoryId(categoryId).map { mapper.toResponse(it) }
    }

    @Transactional
    fun create(request: TaskRequest): TaskResponse {
        log.debug("Criando tarefa: ${request.title}")
        val category = categoryService.findCategoryOrThrow(request.categoryId)
        val task = Task(
            title = request.title,
            description = request.description,
            category = category,
            dateTime = parseDateTime(request.dateTime)
        )
        return mapper.toResponse(taskRepository.save(task))
    }

    @Transactional
    fun update(id: Long, request: TaskRequest): TaskResponse {
        log.debug("Atualizando tarefa id=$id")
        val existing = findTaskOrThrow(id)
        val category = categoryService.findCategoryOrThrow(request.categoryId)
        val updated = existing.copy(
            title = request.title,
            description = request.description,
            category = category,
            dateTime = parseDateTime(request.dateTime)
        )
        return mapper.toResponse(taskRepository.save(updated))
    }

    @Transactional
    fun delete(id: Long) {
        log.debug("Removendo tarefa id=$id")
        val task = findTaskOrThrow(id)
        taskRepository.delete(task)
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private fun findTaskOrThrow(id: Long): Task =
        taskRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Tarefa não encontrada: id=$id") }

    private fun parseDateTime(raw: String): LocalDateTime =
        try {
            LocalDateTime.parse(raw)
        } catch (e: DateTimeParseException) {
            throw InvalidRequestException("Formato de data/hora inválido. Use ISO-8601: 'yyyy-MM-ddTHH:mm:ss'")
        }
}
