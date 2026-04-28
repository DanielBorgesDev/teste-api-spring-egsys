# Task API - EGSys

Projeto teste com o objetivo de avaliar o nível de conhecimento sobre o desenvolvimento de API utilizando Kotlin e Spring Boot.

## Tecnologias Utilizadas

- **Linguagem:** [Kotlin](https://kotlinlang.org/) 1.9.25
- **Framework:** [Spring Boot](https://spring.io/projects/spring-boot) 3.3.5
- **Gerenciador de Dependências:** [Maven](https://maven.apache.org/)
- **Banco de Dados:** [H2 Database](https://www.h2database.com/) (In-memory)
- **Persistência:** Spring Data JPA / Hibernate
- **Segurança:** Spring Security + JWT (jjwt 0.12.6)
- **Validação:** Bean Validation (Hibernate Validator)
- **Testes:** JUnit 5, MockK, Spring MockMvc

## Funcionalidades

- **Autenticação JWT:** Registro e login de usuários com token Bearer stateless.
- **Gerenciamento de Categorias:** CRUD completo para organizar tarefas.
- **Gerenciamento de Tarefas:** Criação, edição, remoção e listagem de tarefas vinculadas a categorias.
- **Filtros Avançados:** Busca de tarefas por categoria específica.
- **Seed de Dados:** Banco de dados populado automaticamente ao iniciar (usuário admin + categorias + tarefas).
- **Tratamento de Erros:** Respostas padronizadas para recursos não encontrados, duplicados ou inválidos.

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

## Autenticação

Todos os endpoints de `/api/categories` e `/api/tasks` são protegidos e requerem um token JWT válido.

### Endpoints públicos

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| POST | `/api/auth/register` | Cadastra um novo usuário |
| POST | `/api/auth/login` | Autentica e retorna o token JWT |

### Usuário padrão (criado automaticamente pelo DataSeeder)

| Campo | Valor |
| :--- | :--- |
| E-mail | `admin@egsys.com` |
| Senha | `admin123` |
| Perfil | `ADMIN` |

### Como usar o token

Após o login, utilize o token retornado no header de todas as requisições:

```
Authorization: Bearer <seu_token_jwt>
```

**Exemplo de login (POST /api/auth/login):**
```json
{
  "email": "admin@egsys.com",
  "password": "admin123"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "email": "admin@egsys.com",
  "name": "Admin",
  "role": "ADMIN"
}
```

## Endpoints da API

> ⚠️ Todos os endpoints abaixo requerem o header `Authorization: Bearer <token>`.

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

**Exemplo de criação de Tarefa (POST /api/tasks):**
```json
{
  "title": "Estudar Spring Boot",
  "description": "Finalizar o desafio técnico",
  "categoryId": 4,
  "dateTime": "2026-04-28T10:00:00"
}
```

## Testando com o Postman

Uma coleção do Postman está incluída no repositório com o fluxo de autenticação configurado automaticamente.

1. Localize o arquivo `postman/TaskAPI_Collection.json`.
2. No Postman, clique em **Import** e selecione este arquivo.
3. Execute **Auth → Login** — o token JWT é salvo automaticamente na variável `{{token}}`.
4. Todos os demais endpoints já utilizam esse token via `Authorization: Bearer {{token}}`.

## H2 Console

Para visualizar o banco de dados em tempo real:
- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:taskdb`
- **User:** `sa`
- **Password:** (em branco)
