package com.egsys.taskapi.application.service

import com.egsys.taskapi.application.dto.TaskRequest
import com.egsys.taskapi.application.exception.ResourceNotFoundException
import com.egsys.taskapi.application.mapper.TaskMapper
import com.egsys.taskapi.domain.model.Category
import com.egsys.taskapi.domain.model.Task
import com.egsys.taskapi.domain.repository.TaskRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*

class TaskServiceTest {

    private val taskRepository = mockk<TaskRepository>()
    private val categoryService = mockk<CategoryService>()
    private val mapper = TaskMapper()
    private val taskService = TaskService(taskRepository, categoryService, mapper)

    @Test
    fun `findAll should return all tasks when categoryId is null`() {
        val category = Category(1, "Trabalho")
        val tasks = listOf(Task(1, "Tarefa 1", null, category, LocalDateTime.now()))
        val pageable = org.springframework.data.domain.Pageable.unpaged()
        val page = org.springframework.data.domain.PageImpl(tasks)
        every { taskRepository.findAll(pageable) } returns page

        val result = taskService.findAll(null, pageable)

        assertEquals(1, result.totalElements)
        assertEquals("Tarefa 1", result.content[0].title)
        verify { taskRepository.findAll(pageable) }
    }

    @Test
    fun `findAll should return filtered tasks when categoryId is provided`() {
        val category = Category(1, "Trabalho")
        val tasks = listOf(Task(1, "Tarefa 1", null, category, LocalDateTime.now()))
        val pageable = org.springframework.data.domain.Pageable.unpaged()
        val page = org.springframework.data.domain.PageImpl(tasks)
        every { taskRepository.findByCategoryId(1, pageable) } returns page

        val result = taskService.findAll(1, pageable)

        assertEquals(1, result.totalElements)
        assertEquals("Tarefa 1", result.content[0].title)
        verify { taskRepository.findByCategoryId(1, pageable) }
    }

    @Test
    fun `create should save and return a new task`() {
        val category = Category(1, "Trabalho")
        val request = TaskRequest("Nova Tarefa", "Desc", 1, "2026-04-28T10:00:00")
        val taskToSave = Task(title = "Nova Tarefa", description = "Desc", category = category, dateTime = LocalDateTime.parse("2026-04-28T10:00:00"))
        val savedTask = taskToSave.copy(id = 1)

        every { categoryService.findCategoryOrThrow(1) } returns category
        every { taskRepository.save(any()) } returns savedTask

        val result = taskService.create(request)

        assertEquals("Nova Tarefa", result.title)
        assertEquals(1, result.category.id)
        verify { taskRepository.save(any()) }
    }

    @Test
    fun `findById should return a task when it exists`() {
        val category = Category(1, "Trabalho")
        val task = Task(1, "Tarefa 1", null, category, LocalDateTime.now())
        every { taskRepository.findById(1) } returns Optional.of(task)

        val result = taskService.findById(1)

        assertEquals("Tarefa 1", result.title)
    }

    @Test
    fun `findById should throw exception when task does not exist`() {
        every { taskRepository.findById(99) } returns Optional.empty()

        assertThrows<ResourceNotFoundException> {
            taskService.findById(99)
        }
    }
}
