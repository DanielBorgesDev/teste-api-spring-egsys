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
        val categories = listOf(Category(1, "Trabalho"), Category(2, "Casa"))
        val pageable = org.springframework.data.domain.Pageable.unpaged()
        val page = org.springframework.data.domain.PageImpl(categories)
        every { repository.findAll(pageable) } returns page

        val result = service.findAll(pageable)

        assertEquals(2, result.totalElements)
        assertEquals("Trabalho", result.content[0].description)
        verify { repository.findAll(pageable) }
    }

    @Test
    fun `create should throw exception when category already exists`() {
        val description = "Casa"
        every { repository.existsByDescriptionIgnoreCase(description) } returns true

        assertThrows<ResourceAlreadyExistsException> {
            service.create(description)
        }
    }

    @Test
    fun `findById should throw exception when category not found`() {
        every { repository.findById(99) } returns Optional.empty()

        assertThrows<ResourceNotFoundException> {
            service.findById(99)
        }
    }
}
