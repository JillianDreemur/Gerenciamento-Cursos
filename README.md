# Sistema de Gerenciamento de Cursos

Sistema completo de gerenciamento de cursos desenvolvido com Spring Boot, incluindo API REST e interface web com Thymeleaf.

## 📋 Requisitos Implementados

✅ **Projeto SPRING - CRUD** (Camadas Controller, Service, Repository e Model)  
✅ **Conexão com Banco JDBC** - PostgreSQL ou H2  
✅ **Documentação Swagger**  
✅ **Collection Postman** com todos endpoints  
✅ **Autenticação com Login e Senha**  
✅ **Tratamento de Exceções**  
✅ **Retorno de status code correto**  
✅ **Autenticação JWT - Spring Security**  
✅ **Interface Gráfica com Thymeleaf**  
✅ **Objeto de Session**  
✅ **Pronto para deploy em nuvem** (Vercel, Netlify, Render, Railway ou outros)

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** com JWT
- **Spring JDBC**
- **Thymeleaf**
- **PostgreSQL / H2**
- **Swagger/OpenAPI**
- **Lombok**
- **Maven**

## 📦 Estrutura do Projeto

```
src/main/java/appGerente/GerenciamentoCursos/
├── config/          # Configurações (Security, Swagger)
├── controller/      # Controllers REST e Web
├── dao/            # Camada de acesso a dados (JDBC)
├── exception/      # Tratamento global de exceções
├── models/         # Entidades e DTOs
├── security/       # Configurações de segurança e JWT
└── services/       # Camada de serviços
```

## 🗄️ Modelos de Dados

- **Usuario**: Login, senha, nome, email, role (ADMIN, PROFESSOR, ALUNO)
- **Aluno**: Nome, email, CPF, telefone
- **Curso**: Nome, descrição, carga horária, datas, status
- **Nota**: Aluno, curso, valor, tipo de avaliação, data, observações
- **Session**: Aluno, curso, data da sessão, conteúdo, duração, status

## 🔧 Configuração

### 1. Banco de Dados

#### Opção 1: PostgreSQL
1. Instale o PostgreSQL
2. Crie um banco de dados:
```sql
CREATE DATABASE gerenciamento_cursos;
```

3. Configure no `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gerenciamento_cursos
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

#### Opção 2: H2 (Banco em memória)
No `application.properties`, altere para:
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
```

### 2. Executar o Projeto

```bash
# Compilar
mvn clean install

# Executar
mvn spring-boot:run
```

O projeto estará disponível em: `http://localhost:8080`

## 🔐 Autenticação

### Usuários Padrão

- **Admin**: 
  - Login: `admin`
  - Senha: `admin123`
  
- **Professor**: 
  - Login: `professor`
  - Senha: `prof123`

### API REST - Autenticação JWT

1. Faça login em `/api/auth/login`:
```json
POST /api/auth/login
{
    "login": "admin",
    "senha": "admin123"
}
```

2. Use o token retornado no header das requisições:
```
Authorization: Bearer {token}
```

## 📚 Documentação Swagger

Acesse a documentação interativa da API em:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

## 🌐 Interface Web

Acesse a interface web em: `http://localhost:8080/login`

Funcionalidades disponíveis:
- Gerenciamento de Alunos
- Gerenciamento de Cursos
- Gerenciamento de Notas
- Gerenciamento de Sessões
- Dashboard

## 📮 Endpoints da API

### Autenticação
- `POST /api/auth/login` - Login e obtenção de token JWT

### Usuários
- `GET /api/usuarios` - Listar todos
- `GET /api/usuarios/{id}` - Buscar por ID
- `POST /api/usuarios` - Criar
- `PUT /api/usuarios/{id}` - Atualizar
- `DELETE /api/usuarios/{id}` - Deletar

### Alunos
- `GET /api/alunos` - Listar todos
- `GET /api/alunos/{id}` - Buscar por ID
- `POST /api/alunos` - Criar
- `PUT /api/alunos/{id}` - Atualizar
- `DELETE /api/alunos/{id}` - Deletar

### Cursos
- `GET /api/cursos` - Listar todos
- `GET /api/cursos/{id}` - Buscar por ID
- `POST /api/cursos` - Criar
- `PUT /api/cursos/{id}` - Atualizar
- `DELETE /api/cursos/{id}` - Deletar

### Notas
- `GET /api/notas` - Listar todas
- `GET /api/notas/{id}` - Buscar por ID
- `GET /api/notas/aluno/{alunoId}` - Buscar por aluno
- `GET /api/notas/curso/{cursoId}` - Buscar por curso
- `POST /api/notas` - Criar
- `PUT /api/notas/{id}` - Atualizar
- `DELETE /api/notas/{id}` - Deletar

### Sessões
- `GET /api/sessions` - Listar todas
- `GET /api/sessions/{id}` - Buscar por ID
- `GET /api/sessions/aluno/{alunoId}` - Buscar por aluno
- `GET /api/sessions/curso/{cursoId}` - Buscar por curso
- `POST /api/sessions` - Criar
- `PUT /api/sessions/{id}` - Atualizar
- `DELETE /api/sessions/{id}` - Deletar

## 📥 Collection Postman

Importe o arquivo `GerenciamentoCursos.postman_collection.json` no Postman.

**Variáveis de ambiente:**
- `baseUrl`: http://localhost:8080
- `token`: Token JWT obtido no login (será preenchido automaticamente)

## ☁️ Deploy em Nuvem

### Railway
1. Crie uma conta no Railway
2. Conecte seu repositório GitHub
3. Configure as variáveis de ambiente:
   - `DATABASE_URL` (PostgreSQL)
   - `JWT_SECRET`
4. Deploy automático

### Render
1. Crie uma conta no Render
2. Conecte seu repositório
3. Configure PostgreSQL e variáveis de ambiente
4. Deploy

### Heroku
1. Instale o Heroku CLI
2. Crie um arquivo `Procfile`:
```
web: java -jar target/GerenciamentoCursos-0.0.1-SNAPSHOT.jar
```
3. Configure PostgreSQL addon
4. Deploy

## 🛠️ Desenvolvimento

### Estrutura de Camadas

1. **Model**: Entidades de domínio
2. **DAO**: Acesso a dados com JDBC
3. **Service**: Lógica de negócio
4. **Controller**: Endpoints REST e Web
5. **Security**: Autenticação e autorização JWT

### Tratamento de Exceções

Todas as exceções são tratadas globalmente pelo `GlobalExceptionHandler`, retornando:
- Status HTTP apropriado
- Mensagem de erro clara
- Formato JSON padronizado

## 📝 Licença

Este projeto foi desenvolvido para fins educacionais.

## 👥 Autor

Desenvolvido como projeto acadêmico de gerenciamento de cursos.

