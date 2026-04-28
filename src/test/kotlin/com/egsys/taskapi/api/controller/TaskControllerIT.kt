package com.egsys.taskapi.api.controller

import com.egsys.taskapi.application.dto.TaskRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "admin@egsys.com", roles = ["ADMIN"])
class TaskControllerIT {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should list all tasks`() {
        mockMvc.get("/api/tasks")
            .andExpect {
                status { isOk() }
                jsonPath("$.content[0].title") { exists() }
            }
    }

    @Test
    fun `should create a new task`() {
        val request = TaskRequest(
            title = "Nova Tarefa",
            description = "Teste de Integração",
            categoryId = 1,
            dateTime = "2026-04-28T10:00:00"
        )

        mockMvc.post("/api/tasks") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                status { isCreated() }
                jsonPath("$.title") { value("Nova Tarefa") }
                jsonPath("$.id") { exists() }
            }
    }

    @Test
    fun `should return 422 when creating task with invalid category`() {
        val request = TaskRequest(
            title = "Nova Tarefa",
            description = "Teste de Integração",
            categoryId = 999,
            dateTime = "2026-04-28T10:00:00"
        )

        mockMvc.post("/api/tasks") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                status { isNotFound() }
            }
    }
}
