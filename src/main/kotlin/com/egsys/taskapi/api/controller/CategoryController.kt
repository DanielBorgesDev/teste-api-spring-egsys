package com.egsys.taskapi.api.controller

import com.egsys.taskapi.application.dto.CategoryResponse
import com.egsys.taskapi.application.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Expõe os endpoints REST para gerenciamento de categorias.
 *
 * GET    /api/categories          — lista todas
 * GET    /api/categories/{id}     — busca por id
 * POST   /api/categories          — cria nova
 * PUT    /api/categories/{id}     — atualiza
 * DELETE /api/categories/{id}     — remove
 */
@RestController
@RequestMapping("/api/categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    fun listAll(): ResponseEntity<List<CategoryResponse>> =
        ResponseEntity.ok(categoryService.findAll())

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<CategoryResponse> =
        ResponseEntity.ok(categoryService.findById(id))

    @PostMapping
    fun create(@RequestBody body: Map<String, String>): ResponseEntity<CategoryResponse> {
        val description = body["description"]?.trim()
            ?: return ResponseEntity.badRequest().build()
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(categoryService.create(description))
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody body: Map<String, String>
    ): ResponseEntity<CategoryResponse> {
        val description = body["description"]?.trim()
            ?: return ResponseEntity.badRequest().build()
        return ResponseEntity.ok(categoryService.update(id, description))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        categoryService.delete(id)
        return ResponseEntity.noContent().build()
    }
}
