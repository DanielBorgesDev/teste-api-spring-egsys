package com.egsys.taskapi.infrastructure.config

import com.egsys.taskapi.domain.model.Category
import com.egsys.taskapi.domain.model.Role
import com.egsys.taskapi.domain.model.Task
import com.egsys.taskapi.domain.model.User
import com.egsys.taskapi.domain.repository.CategoryRepository
import com.egsys.taskapi.domain.repository.TaskRepository
import com.egsys.taskapi.domain.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * Popula o banco de dados com dados iniciais ao iniciar a aplicação.
 * Facilita testes manuais sem necessidade de scripts SQL externos.
 */
@Component
class DataSeeder(
    private val categoryRepository: CategoryRepository,
    private val taskRepository: TaskRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(DataSeeder::class.java)

    override fun run(args: ApplicationArguments) {
        log.info("Iniciando seed de dados...")
        seedUsers()
        seedCategories()
    }

    private fun seedUsers() {
        if (userRepository.count() > 0) return
        userRepository.save(
            User(
                name = "Admin",
                email = "admin@egsys.com",
                password = passwordEncoder.encode("admin123"),
                role = Role.ADMIN
            )
        )
        log.info("Usuário admin criado: admin@egsys.com / admin123")
    }

    private fun seedCategories() {

        val categories = categoryRepository.saveAll(
            listOf(
                Category(description = "Casa"),
                Category(description = "Trabalho"),
                Category(description = "Saúde"),
                Category(description = "Estudos"),
                Category(description = "Lazer"),
                Category(description = "Financeiro")
            )
        )

        val casa = categories[0]
        val trabalho = categories[1]
        val saude = categories[2]
        val estudos = categories[3]
        val lazer = categories[4]
        val financeiro = categories[5]

        taskRepository.saveAll(
            listOf(
                Task(
                    title = "Limpar a casa",
                    description = "Varrer, passar pano e organizar os quartos",
                    category = casa,
                    dateTime = LocalDateTime.now().plusDays(1)
                ),
                Task(
                    title = "Fazer compras no mercado",
                    category = casa,
                    dateTime = LocalDateTime.now().plusDays(2)
                ),
                Task(
                    title = "Reunião de planejamento",
                    description = "Alinhamento de sprint com o time",
                    category = trabalho,
                    dateTime = LocalDateTime.now().plusHours(3)
                ),
                Task(
                    title = "Entregar relatório mensal",
                    category = trabalho,
                    dateTime = LocalDateTime.now().plusDays(5)
                ),
                Task(
                    title = "Consulta médica",
                    description = "Check-up anual com o clínico geral",
                    category = saude,
                    dateTime = LocalDateTime.now().plusDays(7)
                ),
                Task(
                    title = "Revisar capítulo de algoritmos",
                    description = "Foco em ordenação e busca binária",
                    category = estudos,
                    dateTime = LocalDateTime.now().plusDays(3)
                ),
                Task(
                    title = "Assistir série nova",
                    category = lazer,
                    dateTime = LocalDateTime.now().plusDays(4)
                ),
                Task(
                    title = "Pagar contas do mês",
                    description = "Água, luz, internet e cartão",
                    category = financeiro,
                    dateTime = LocalDateTime.now().plusDays(2)
                )
            )
        )

        log.info("Seed concluído: ${categories.size} categorias e 8 tarefas criadas.")
    }
}
