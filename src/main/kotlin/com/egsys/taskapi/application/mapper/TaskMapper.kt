package com.egsys.taskapi.application.mapper

import com.egsys.taskapi.application.dto.CategoryResponse
import com.egsys.taskapi.application.dto.TaskResponse
import com.egsys.taskapi.domain.model.Category
import com.egsys.taskapi.domain.model.Task
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter


@Component
class TaskMapper {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    fun Category.toResponse(): CategoryResponse =
        CategoryResponse(
            id = this.id,
            description = this.description
        )

    fun Task.toResponse(): TaskResponse =
        TaskResponse(
            id = this.id,
            title = this.title,
            description = this.description,
            category = this.category.toResponse(),
            dateTime = this.dateTime.format(formatter)
        )
}
