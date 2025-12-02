# 🧪 Como Verificar se Está Funcionando

## ✅ Passo 1: Executar a Aplicação

No terminal, execute:

```bash
mvn spring-boot:run
```

**Ou se preferir compilar primeiro:**

```bash
mvn clean install
java -jar target/GerenciamentoCursos-0.0.1-SNAPSHOT.jar
```

## ✅ Passo 2: Verificar se Iniciou Corretamente

Procure por esta mensagem no console:

```
Started GerenciamentoCursosApplication in X.XXX seconds
```

Se aparecer algum erro de conexão com banco de dados, veja a seção "Problemas Comuns" abaixo.

## ✅ Passo 3: Testar a Interface Web

Abra seu navegador e acesse:

- **Página de Login**: http://localhost:8080/login
- **Dashboard**: http://localhost:8080/dashboard (após login)

**Credenciais padrão:**
- Admin: `admin` / `admin123`
- Professor: `professor` / `prof123`

## ✅ Passo 4: Testar a API REST

### Opção A: Swagger (Mais Fácil)

Acesse: **http://localhost:8080/swagger-ui.html**

Você verá todos os endpoints disponíveis e pode testá-los diretamente pela interface.

### Opção B: Postman

1. Importe o arquivo `GerenciamentoCursos.postman_collection.json`
2. Configure a variável `baseUrl` como `http://localhost:8080`
3. Primeiro faça login em `/api/auth/login` para obter o token
4. Teste os outros endpoints

### Opção C: cURL (Terminal)

**1. Fazer Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"login\":\"admin\",\"senha\":\"admin123\"}"
```

**2. Usar o token retornado para acessar outros endpoints:**
```bash
curl -X GET http://localhost:8080/api/alunos \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

## ✅ Passo 5: Verificar Endpoints Principais

Teste estes endpoints para confirmar que tudo está funcionando:

1. **Health Check** (se existir): `GET http://localhost:8080/actuator/health`
2. **API Docs**: `GET http://localhost:8080/api-docs`
3. **Swagger UI**: `GET http://localhost:8080/swagger-ui.html`
4. **Login**: `POST http://localhost:8080/api/auth/login`

## 🔧 Problemas Comuns

### Erro: "Connection refused" ou "Cannot connect to database"

**Solução 1: Usar H2 (Banco em memória - mais fácil para testar)**

Edite `src/main/resources/application.properties` e altere:

```properties
# Comente ou remova as linhas do PostgreSQL e adicione:
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
```

**Solução 2: Configurar PostgreSQL corretamente**

1. Certifique-se que o PostgreSQL está rodando
2. Crie o banco: `CREATE DATABASE gerenciamento_cursos;`
3. Verifique usuário e senha no `application.properties`

### Erro: "Port 8080 already in use"

Altere a porta no `application.properties`:
```properties
server.port=8081
```

### Aplicação não inicia

Verifique:
- Java 17 instalado: `java -version`
- Maven instalado: `mvn -version`
- Dependências baixadas: `mvn clean install`

## 📊 Sinais de que Está Funcionando

✅ Console mostra "Started GerenciamentoCursosApplication"  
✅ Acessa http://localhost:8080/login sem erro  
✅ Swagger UI carrega em http://localhost:8080/swagger-ui.html  
✅ Login retorna token JWT  
✅ Endpoints retornam dados (não erro 500)  

## 🎯 Teste Rápido (1 minuto)

1. Execute: `mvn spring-boot:run`
2. Abra: http://localhost:8080/swagger-ui.html
3. Teste o endpoint `/api/auth/login` com:
   ```json
   {
     "login": "admin",
     "senha": "admin123"
   }
   ```
4. Se retornar um token, **está funcionando!** 🎉

