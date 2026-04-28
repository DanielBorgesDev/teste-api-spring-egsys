package com.egsys.taskapi.application.dto


data class TaskResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val category: CategoryResponse,
    val dateTime: String
)
