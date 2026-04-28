package com.egsys.taskapi.domain.repository

import com.egsys.taskapi.domain.model.Task
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, Long> {

    @Query(
        value = "SELECT t FROM Task t JOIN FETCH t.category",
        countQuery = "SELECT COUNT(t) FROM Task t"
    )
    fun findAllWithCategory(pageable: Pageable): Page<Task>

    @Query(
        value = "SELECT t FROM Task t JOIN FETCH t.category WHERE t.category.id = :categoryId",
        countQuery = "SELECT COUNT(t) FROM Task t WHERE t.category.id = :categoryId"
    )
    fun findByCategoryId(categoryId: Long, pageable: Pageable): Page<Task>
}
