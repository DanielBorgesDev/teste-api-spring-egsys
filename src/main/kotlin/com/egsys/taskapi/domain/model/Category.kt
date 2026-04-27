package com.egsys.taskapi.domain.model

import jakarta.persistence.*

/**
 * Representa uma categoria de tarefa.
 * Exemplos: Casa, Trabalho, Saúde, Estudos, Lazer, Financeiro.
 */
@Entity
@Table(name = "categories")
data class Category(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, length = 100)
    val description: String
)
