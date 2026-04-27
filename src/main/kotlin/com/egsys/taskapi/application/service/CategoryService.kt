package com.egsys.taskapi.application.service

import com.egsys.taskapi.application.dto.CategoryResponse
import com.egsys.taskapi.application.exception.ResourceAlreadyExistsException
import com.egsys.taskapi.application.exception.ResourceNotFoundException
import com.egsys.taskapi.application.mapper.TaskMapper
import com.egsys.taskapi.domain.model.Category
import com.egsys.taskapi.domain.repository.CategoryRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Contém a lógica de negócio relacionada a categorias de tarefa.
 */
@Service
@Transactional(readOnly = true)
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val mapper: TaskMapper
) {

    private val log = LoggerFactory.getLogger(CategoryService::class.java)

    fun findAll(): List<CategoryResponse> {
        log.debug("Buscando todas as categorias")
        return with(mapper) {
            categoryRepository.findAll().map { it.toResponse() }
        }
    }

    fun findById(id: Long): CategoryResponse {
        log.debug("Buscando categoria id=$id")
        val category = findCategoryOrThrow(id)
        return with(mapper) {
            category.toResponse()
        }
    }

    @Transactional
    fun create(description: String): CategoryResponse {
        log.debug("Criando categoria: $description")
        if (categoryRepository.existsByDescriptionIgnoreCase(description)) {
            throw ResourceAlreadyExistsException("Categoria '$description' já existe")
        }
        val saved = categoryRepository.save(Category(description = description))
        return with(mapper) {
            saved.toResponse()
        }
    }

    @Transactional
    fun update(id: Long, description: String): CategoryResponse {
        log.debug("Atualizando categoria id=$id")
        val category = findCategoryOrThrow(id)
        
        val isDuplicate = categoryRepository.existsByDescriptionIgnoreCase(description) && 
                         !category.description.equals(description, ignoreCase = true)
        
        if (isDuplicate) {
            throw ResourceAlreadyExistsException("Categoria '$description' já existe")
        }
        
        val updated = categoryRepository.save(category.copy(description = description))
        return with(mapper) {
            updated.toResponse()
        }
    }

    @Transactional
    fun delete(id: Long) {
        log.debug("Removendo categoria id=$id")
        val category = findCategoryOrThrow(id)
        categoryRepository.delete(category)
    }

    fun findCategoryOrThrow(id: Long): Category =
        categoryRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Categoria não encontrada: id=$id") }
}
