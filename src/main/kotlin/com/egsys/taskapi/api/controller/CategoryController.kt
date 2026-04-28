package com.egsys.taskapi.api.controller

import com.egsys.taskapi.application.dto.CategoryRequest
import com.egsys.taskapi.application.dto.CategoryResponse
import com.egsys.taskapi.application.service.CategoryService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Expõe os endpoints REST para gerenciamento de categorias.
 */
@RestController
@RequestMapping("/api/categories")
@SecurityRequirement(name = "bearerAuth")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    fun listAll(): ResponseEntity<List<CategoryResponse>> =
        ResponseEntity.ok(categoryService.findAll())

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<CategoryResponse> =
        ResponseEntity.ok(categoryService.findById(id))

    @PostMapping
    fun create(@RequestBody @Valid request: CategoryRequest): ResponseEntity<CategoryResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(categoryService.create(request.description))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid request: CategoryRequest
    ): ResponseEntity<CategoryResponse> {
        return ResponseEntity.ok(categoryService.update(id, request.description))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        categoryService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
