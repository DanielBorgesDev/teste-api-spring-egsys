package com.egsys.taskapi.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "tasks")
data class Task(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 200)
    val title: String,

    @Column(length = 1000)
    val description: String? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category,

    @Column(name = "date_time", nullable = false)
    val dateTime: LocalDateTime
)
