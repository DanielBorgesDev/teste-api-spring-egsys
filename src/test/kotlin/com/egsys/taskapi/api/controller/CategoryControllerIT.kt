package com.egsys.taskapi.api.controller

import com.egsys.taskapi.application.dto.CategoryRequest
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
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "test@test.com", roles = ["USER"])
class CategoryControllerIT {


    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should list categories from seeder`() {
        mockMvc.get("/api/categories")
            .andExpect {
                status { isOk() }
                jsonPath("$[0].description") { value("Casa") }
            }
    }

    @Test
    fun `should create new category`() {
        val request = CategoryRequest(description = "Nova Categoria")

        mockMvc.post("/api/categories") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                status { isCreated() }
                jsonPath("$.description") { value("Nova Categoria") }
                jsonPath("$.id") { exists() }
            }
    }

    @Test
    fun `should return 422 when creating category with empty description`() {
        val request = CategoryRequest(description = "")

        mockMvc.post("/api/categories") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                status { isUnprocessableEntity() }
                jsonPath("$.error") { value("Unprocessable Entity") }
            }
    }
}
