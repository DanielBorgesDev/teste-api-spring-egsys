package com.egsys.taskapi.application.dto

/**
 * DTO de resposta para Task.
 */
data class TaskResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val category: CategoryResponse,
    val dateTime: String   // ISO-8601
)
