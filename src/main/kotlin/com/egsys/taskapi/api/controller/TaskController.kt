package com.egsys.taskapi.api.controller

import com.egsys.taskapi.application.dto.TaskRequest
import com.egsys.taskapi.application.dto.TaskResponse
import com.egsys.taskapi.application.service.TaskService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Expõe os endpoints REST para o CRUD de tarefas.
 *
 * GET    /api/tasks               — lista todas as tarefas
 * GET    /api/tasks/{id}          — busca tarefa por id
 * GET    /api/tasks?categoryId=X  — filtra por categoria
 * POST   /api/tasks               — cria tarefa
 * PUT    /api/tasks/{id}          — atualiza tarefa
 * DELETE /api/tasks/{id}          — remove tarefa
 */
@RestController
@RequestMapping("/api/tasks")
class TaskController(private val taskService: TaskService) {

    @GetMapping
    fun listAll(
        @RequestParam(required = false) categoryId: Long?
    ): ResponseEntity<List<TaskResponse>> {
        return ResponseEntity.ok(taskService.findAll(categoryId))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<TaskResponse> =
        ResponseEntity.ok(taskService.findById(id))

    @PostMapping
    fun create(@Valid @RequestBody request: TaskRequest): ResponseEntity<TaskResponse> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(taskService.create(request))

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: TaskRequest
    ): ResponseEntity<TaskResponse> =
        ResponseEntity.ok(taskService.update(id, request))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        taskService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
