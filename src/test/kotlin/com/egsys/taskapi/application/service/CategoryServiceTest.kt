package com.egsys.taskapi.application.service

import com.egsys.taskapi.application.exception.ResourceAlreadyExistsException
import com.egsys.taskapi.application.exception.ResourceNotFoundException
import com.egsys.taskapi.application.mapper.TaskMapper
import com.egsys.taskapi.domain.model.Category
import com.egsys.taskapi.domain.repository.CategoryRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.assertEquals

class CategoryServiceTest {

    private val repository = mockk<CategoryRepository>()
    private val mapper = TaskMapper()
    private val service = CategoryService(repository, mapper)

    @Test
    fun `findAll should return list of categories`() {
        // Given
        val categories = listOf(Category(1, "Trabalho"), Category(2, "Casa"))
        every { repository.findAll() } returns categories

        // When
        val result = service.findAll()

        // Then
        assertEquals(2, result.size)
        assertEquals("Trabalho", result[0].description)
        verify { repository.findAll() }
    }

    @Test
    fun `create should throw exception when category already exists`() {
        // Given
        val description = "Casa"
        every { repository.existsByDescriptionIgnoreCase(description) } returns true

        // When & Then
        assertThrows<ResourceAlreadyExistsException> {
            service.create(description)
        }
    }

    @Test
    fun `findById should throw exception when category not found`() {
        // Given
        every { repository.findById(99) } returns Optional.empty()

        // When & Then
        assertThrows<ResourceNotFoundException> {
            service.findById(99)
        }
    }
}
