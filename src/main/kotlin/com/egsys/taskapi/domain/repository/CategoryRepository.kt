package com.egsys.taskapi.domain.repository

import com.egsys.taskapi.domain.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun existsByDescriptionIgnoreCase(description: String): Boolean
}
