# Task API - EGSys

Projeto teste com o objetivo de avaliar o nível de conhecimento sobre o desenvolvimento de API utilizando Kotlin e Spring Boot.

## Tecnologias Utilizadas

- **Linguagem:** Kotlin 1.9.25
- **Framework:** Spring Boot 3.3.5
- **Gerenciador de Dependências:** Maven
- **Banco de Dados:** H2 Database (In-memory)
- **Persistência:** Spring Data JPA / Hibernate
- **Segurança:** Spring Security + JWT (jjwt 0.12.6)
- **Validação:** Bean Validation (Hibernate Validator)
- **Testes:** JUnit 5, MockK, Spring MockMvc

## 🌟 Destaques Técnicos (Para Avaliação)

- **Documentação Interativa (Swagger UI):** Interface gráfica gerada automaticamente (OpenAPI) para explorar e testar todos os endpoints da API de forma intuitiva.
- **Segurança Robusta (Spring Security + JWT):** Sistema de autenticação stateless com tokens Bearer, protegendo as rotas sensíveis e extraindo o perfil do usuário logado através do contexto de segurança.
- **Tratamento de Erros Global:** Utilização de `@RestControllerAdvice` e `ExceptionHandler` para capturar exceções e padronizar as respostas de erro HTTP (ex: 404 Not Found, 409 Conflict, 422 Unprocessable Entity).
- **Data Seeding Inteligente:** População automática do banco de dados na inicialização (`ApplicationRunner`), inserindo um usuário Admin, categorias predefinidas e tarefas, garantindo que a aplicação esteja pronta para testes imediatamente.
- **Arquitetura em Camadas:** Separação clara de responsabilidades (Controllers, Services, Repositories) com utilização de DTOs e Mappers para não expor as entidades de domínio diretamente nas respostas da API.
- **Validações Consistentes:** Uso de Bean Validation (Hibernate Validator) para garantir a integridade dos dados na entrada das requisições (ex: obrigatoriedade de campos, limites de caracteres e formato de e-mail).

## Funcionalidades

- **Autenticação:** Registro, login de usuários e consulta de dados do perfil (`/api/auth/me`).
- **Gerenciamento de Categorias:** CRUD completo de categorias de tarefas.
- **Gerenciamento de Tarefas:** CRUD de tarefas com relacionamento com categorias e filtro por ID da categoria.

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

>  Todos os endpoints abaixo requerem o header `Authorization: Bearer <token>`.

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

##  Documentação Interativa (Swagger UI)

A API possui uma documentação interativa integrada, o que facilita imensamente o teste de todas as rotas sem a necessidade de ferramentas externas.

1. Inicie a aplicação.
2. Acesse no navegador: `http://localhost:8080/swagger-ui.html`
3. Como testar rotas protegidas pelo Swagger:
   - Expanda a seção **Auth** e use o endpoint `POST /api/auth/login` com as credenciais padrão (admin@egsys.com / admin123).
   - Copie o token retornado na resposta.
   - Suba até o topo da página, clique no botão verde **Authorize**.
   - Cole o token no campo de texto e clique em "Authorize". Agora você pode testar qualquer rota protegida!

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
