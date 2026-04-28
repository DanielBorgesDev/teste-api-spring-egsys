package com.egsys.taskapi.application.service

import com.egsys.taskapi.application.dto.TaskRequest
import com.egsys.taskapi.application.dto.TaskResponse
import com.egsys.taskapi.application.exception.ResourceNotFoundException
import com.egsys.taskapi.application.mapper.TaskMapper
import com.egsys.taskapi.domain.model.Task
import com.egsys.taskapi.domain.repository.TaskRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
@Transactional(readOnly = true)
class TaskService(
    private val taskRepository: TaskRepository,
    private val categoryService: CategoryService,
    private val mapper: TaskMapper
) {

    private val log = LoggerFactory.getLogger(TaskService::class.java)

    fun findAll(categoryId: Long?): List<TaskResponse> {
        log.debug("Buscando tarefas. Filtro categoryId=$categoryId")
        val tasks = if (categoryId != null) {
            taskRepository.findByCategoryId(categoryId)
        } else {
            taskRepository.findAll()
        }
        
        return with(mapper) {
            tasks.map { it.toResponse() }
        }
    }

    fun findById(id: Long): TaskResponse {
        log.debug("Buscando tarefa id=$id")
        val task = findTaskOrThrow(id)
        return with(mapper) {
            task.toResponse()
        }
    }

    @Transactional
    fun create(request: TaskRequest): TaskResponse {
        log.debug("Criando tarefa: ${request.title}")
        val category = categoryService.findCategoryOrThrow(request.categoryId)
        
        val task = Task(
            title = request.title,
            description = request.description,
            category = category,
            dateTime = LocalDateTime.parse(request.dateTime)
        )
        
        val saved = taskRepository.save(task)
        return with(mapper) {
            saved.toResponse()
        }
    }

    @Transactional
    fun update(id: Long, request: TaskRequest): TaskResponse {
        log.debug("Atualizando tarefa id=$id")
        val task = findTaskOrThrow(id)
        val category = categoryService.findCategoryOrThrow(request.categoryId)
        
        val updatedTask = task.copy(
            title = request.title,
            description = request.description,
            category = category,
            dateTime = LocalDateTime.parse(request.dateTime)
        )
        
        val saved = taskRepository.save(updatedTask)
        return with(mapper) {
            saved.toResponse()
        }
    }

    @Transactional
    fun delete(id: Long) {
        log.debug("Removendo tarefa id=$id")
        val task = findTaskOrThrow(id)
        taskRepository.delete(task)
    }

    private fun findTaskOrThrow(id: Long): Task =
        taskRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Tarefa não encontrada: id=$id") }
}
