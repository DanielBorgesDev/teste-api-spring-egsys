package com.egsys.taskapi.domain.repository

import com.egsys.taskapi.domain.model.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t JOIN FETCH t.category")
    fun findAllWithCategory(): List<Task>

    @Query("SELECT t FROM Task t JOIN FETCH t.category WHERE t.category.id = :categoryId")
    fun findByCategoryId(categoryId: Long): List<Task>
}
