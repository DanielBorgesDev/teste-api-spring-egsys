package com.egsys.taskapi.api.controller

import com.egsys.taskapi.application.dto.TaskRequest
import com.egsys.taskapi.application.dto.TaskResponse
import com.egsys.taskapi.application.service.TaskService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault

@RestController
@RequestMapping("/api/tasks")
@SecurityRequirement(name = "bearerAuth")
class TaskController(private val taskService: TaskService) {

    @GetMapping
    fun listAll(
        @RequestParam(required = false) categoryId: Long?,
        @ParameterObject @PageableDefault(size = 10, sort = ["dateTime"]) pageable: Pageable
    ): ResponseEntity<Page<TaskResponse>> {
        return ResponseEntity.ok(taskService.findAll(categoryId, pageable))
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
