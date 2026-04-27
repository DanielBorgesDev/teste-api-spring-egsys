# Task API - EGSys

Uma API RESTful robusta desenvolvida em Kotlin e Spring Boot para gerenciamento de tarefas e categorias.

## Tecnologias Utilizadas

- **Linguagem:** [Kotlin](https://kotlinlang.org/) 1.9.25
- **Framework:** [Spring Boot](https://spring.io/projects/spring-boot) 3.3.5
- **Gerenciador de Dependências:** [Maven](https://maven.apache.org/)
- **Banco de Dados:** [H2 Database](https://www.h2database.com/) (In-memory)
- **Persistência:** Spring Data JPA / Hibernate
- **Validação:** Bean Validation (Hibernate Validator)
- **Documentação:** README estruturado

## Funcionalidades

- **Gerenciamento de Categorias:** CRUD completo para organizar tarefas.
- **Gerenciamento de Tarefas:** Criação, edição, remoção e listagem de tarefas vinculadas a categorias.
- **Filtros Avançados:** Busca de tarefas por categoria específica.
- **Seed de Dados:** Banco de dados populado automaticamente ao iniciar para facilitar os testes.
- **Tratamento de Erros:** Respostas padronizadas para recursos não encontrados ou duplicados.

## Como Executar

### Pré-requisitos
- JDK 21 ou superior
- Maven 3.x instalado

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/DanielBorgesDev/teste-api-spring-egsys.git
   ```
2. Entre na pasta do projeto:
   ```bash
   cd teste-api-spring-egsys
   ```
3. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```
4. A API estará disponível em: `http://localhost:8080`

## Endpoints da API

### Categorias (`/api/categories`)
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| GET | `/api/categories` | Lista todas as categorias |
| GET | `/api/categories/{id}` | Busca categoria por ID |
| POST | `/api/categories` | Cria uma nova categoria |
| PUT | `/api/categories/{id}` | Atualiza uma categoria existente |
| DELETE | `/api/categories/{id}` | Remove uma categoria |

### Tarefas (`/api/tasks`)
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| GET | `/api/tasks` | Lista todas as tarefas |
| GET | `/api/tasks?categoryId={id}` | Filtra tarefas por categoria |
| GET | `/api/tasks/{id}` | Busca tarefa por ID |
| POST | `/api/tasks` | Cria uma nova tarefa |
| PUT | `/api/tasks/{id}` | Atualiza uma tarefa existente |
| DELETE | `/api/tasks/{id}` | Remove uma tarefa |

## Testando a API

### Postman / Insomnia
Você pode utilizar o Postman para disparar as requisições. Para os métodos `POST` e `PUT`, utilize o corpo (Body) em formato `JSON`.

**Exemplo de criação de Tarefa (POST):**
```json
{
  "title": "Estudar Spring Boot",
  "description": "Finalizar o desafio técnico",
  "categoryId": 4,
  "dateTime": "2026-04-28T10:00:00"
}
```

### H2 Console
Para visualizar o banco de dados em tempo real:
- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:taskdb`
- **User:** `sa`
- **Password:** (em branco)

---
Desenvolvido por [Daniel](https://github.com/DanielBorgesDev)
