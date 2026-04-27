package com.egsys.taskapi

import com.egsys.taskapi.application.dto.TaskRequest
import com.egsys.taskapi.application.service.CategoryService
import com.egsys.taskapi.application.service.TaskService
import com.egsys.taskapi.domain.model.Category
import com.egsys.taskapi.domain.repository.CategoryRepository
import com.egsys.taskapi.domain.repository.TaskRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@SpringBootTest
@Transactional
class TaskApiApplicationTests {

    @Autowired
    lateinit var categoryService: CategoryService

    @Autowired
    lateinit var taskService: TaskService

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Autowired
    lateinit var taskRepository: TaskRepository

    private lateinit var savedCategory: Category

    @BeforeEach
    fun setup() {
        savedCategory = categoryRepository.save(Category(description = "Teste"))
    }

    @AfterEach
    fun cleanup() {
        taskRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    @Test
    fun `deve listar todas as categorias`() {
        val categories = categoryService.findAll()
        assertTrue(categories.isNotEmpty())
        assertTrue(categories.any { it.description == "Teste" })
    }

    @Test
    fun `deve criar e buscar uma tarefa`() {
        val request = TaskRequest(
            title = "Tarefa de teste",
            description = "Descrição de teste",
            categoryId = savedCategory.id,
            dateTime = LocalDateTime.now().plusDays(1).toString()
        )

        val created = taskService.create(request)
        assertNotNull(created.id)
        assertEquals("Tarefa de teste", created.title)
        assertEquals("Teste", created.category.description)

        val found = taskService.findById(created.id)
        assertEquals(created.id, found.id)
    }

    @Test
    fun `deve atualizar uma tarefa`() {
        val request = TaskRequest(
            title = "Tarefa original",
            categoryId = savedCategory.id,
            dateTime = LocalDateTime.now().plusDays(1).toString()
        )
        val created = taskService.create(request)

        val updateRequest = TaskRequest(
            title = "Tarefa atualizada",
            description = "Nova descrição",
            categoryId = savedCategory.id,
            dateTime = LocalDateTime.now().plusDays(2).toString()
        )
        val updated = taskService.update(created.id, updateRequest)

        assertEquals("Tarefa atualizada", updated.title)
        assertEquals("Nova descrição", updated.description)
    }

    @Test
    fun `deve deletar uma tarefa`() {
        val request = TaskRequest(
            title = "Tarefa para deletar",
            categoryId = savedCategory.id,
            dateTime = LocalDateTime.now().plusDays(1).toString()
        )
        val created = taskService.create(request)
        taskService.delete(created.id)

        val allTasks = taskService.findAll()
        assertTrue(allTasks.none { it.id == created.id })
    }

    @Test
    fun `deve filtrar tarefas por categoria`() {
        val outraCategoria = categoryRepository.save(Category(description = "Outra"))

        taskService.create(TaskRequest("T1", null, savedCategory.id, LocalDateTime.now().plusDays(1).toString()))
        taskService.create(TaskRequest("T2", null, savedCategory.id, LocalDateTime.now().plusDays(1).toString()))
        taskService.create(TaskRequest("T3", null, outraCategoria.id, LocalDateTime.now().plusDays(1).toString()))

        val filtered = taskService.findByCategoryId(savedCategory.id)
        assertEquals(2, filtered.size)
        assertTrue(filtered.all { it.category.id == savedCategory.id })
    }
}
