# Backend - API de Produtos

API REST Spring Boot para gerenciamento de produtos com autenticação JWT e PostgreSQL.

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** para autenticação
- **Spring Data JPA** para persistência
- **PostgreSQL** como banco de dados
- **JWT** para autenticação stateless
- **Lombok** para redução de boilerplate
- **Swagger/OpenAPI** para documentação
- **Maven** para gerenciamento de dependências

## 📋 Pré-requisitos

- Java 17+
- Maven 3.6+
- PostgreSQL 17+
- Docker (opcional)

## 🛠️ Instalação

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd backend
```

2. Configure o banco de dados:
```bash
# Crie o arquivo .env na raiz do projeto
cat > .env << EOF
# Database Configuration
DB_NAME=produtos_db
DB_USER=produtos_user
DB_PASSWORD=admin123

# JWT Configuration
JWT_SECRET=NCwcNkdgXfGmXG2y7Mtc3VVLOOXNU2U8zyspLnNX9MAv5MGn2bhi48AsLYEIuM6mYw0CCetdpEfweKNaYcf8lwO6it1AX5XrGj1h7rFA2ekQZUOecT3Jd8IFheSo0OmkLiRaB0mTbYdAqCpV4hdxbHs7CbGBQNk139VkKfMMBcSE9Caz5ictcKUWCoj4bEIdQakoPr00ZGPjfKKkXU8EyaD3028RpIEAOYyD2KGoyzopqCVLLZvBCJapmPga2OPcr1ujx1q8Wyv3SFvg7MPK6GSHb4vKFnUbQdMmP7u9CACD8QZsiwuG0kRo65mflXmprc3PWleMIsUwtAvbLzD445vnkpx8Pslk
EOF
```

3. Configure o PostgreSQL:
```sql
-- Conecte ao PostgreSQL como superuser
CREATE DATABASE produtos_db;
CREATE USER produtos_user WITH PASSWORD 'adm123';
GRANT ALL PRIVILEGES ON DATABASE produtos_db TO produtos_user;
```

## 🚀 Executando a aplicação

### Desenvolvimento Local

1. **Com Maven:**
```bash
# Compilar o projeto
mvn clean compile

# Executar a aplicação
mvn spring-boot:run
```

2. **Com JAR:**
```bash
# Gerar JAR
mvn clean package -DskipTests

# Executar JAR
java -jar target/teste-pratico-api-0.0.1-SNAPSHOT.jar
```

3. **Com IDE:**
- Importe o projeto como projeto Maven
- Execute a classe `TestePraticoApiApplication.java`

### Docker

1. **Docker Compose (com PostgreSQL):**
```bash
docker-compose up --build
```
- Este comando ira iniciar o Banco de Dados, Backend e Frontend


## 📁 Estrutura do Projeto

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/testepratico/
│   │   │   ├── auth/                    # Módulo de autenticação
│   │   │   │   ├── dto/                 # DTOs de autenticação
│   │   │   │   ├── security/            # Configurações de segurança
│   │   │   │   └── service/impl/        # Implementações de serviços
│   │   │   ├── products/                # Módulo de produtos
│   │   │   │   ├── dto/                 # DTOs de produtos
│   │   │   │   ├── model/               # Entidades JPA
│   │   │   │   ├── repository/          # Repositórios JPA
│   │   │   │   ├── service/impl/        # Implementações de serviços
│   │   │   │   └── controller/          # Controllers REST
│   │   │   ├── users/                   # Módulo de usuários
│   │   │   │   ├── model/               # Entidades JPA
│   │   │   │   ├── repository/          # Repositórios JPA
│   │   │   │   ├── service/impl/        # Implementações de serviços
│   │   │   │   └── controller/          # Controllers REST
│   │   │   ├── common/                  # Utilitários comuns
│   │   │   │   ├── health/              # Health checks
│   │   │   │   └── util/                # Utilitários
│   │   │   ├── config/                  # Configurações
│   │   │   ├── utils/                   # Utilitários gerais
│   │   │   └── TestePraticoApiApplication.java
│   │   └── resources/
│   │       └── application.yml          # Configurações da aplicação
│   └── test/                            # Testes
├── pom.xml                              # Dependências Maven
├── Dockerfile                           # Imagem Docker
├── docker-compose.yml                   # Orquestração Docker
└── README.md
```

## 🔧 Configuração

### application.yml

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5435/teste_pratico
    username: adm
    password: adm123
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

jwt:
  secret: ${JWT_SECRET}
  expiration: 86400000
```

### Variáveis de Ambiente

| Variável | Descrição | Padrão |
|----------|-----------|---------|
| `SPRING_DATASOURCE_URL` | URL do banco de dados | `jdbc:postgresql://localhost:5432/produtos_db` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco | `produtos_user` |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco | `admin123` |
| `JWT_SECRET` | Chave secreta do JWT | - |
| `JWT_EXPIRATION` | Expiração do token (ms) | `86400000` |

## 🔐 Autenticação

### Endpoints de Autenticação

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/auth/login` | Login do usuário |
| POST | `/api/users/register` | Registro de novo usuário |

### Exemplo de Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "adm",
    "password": "adm123"
  }'
```

### Resposta

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "admin"
}
```

## 📊 Endpoints da API

### Produtos

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/api/products` | Listar produtos | ✅ |
| POST | `/api/products` | Criar produto | ✅ |
| GET | `/api/products/{id}` | Buscar produto | ✅ |
| PUT | `/api/products/{id}` | Atualizar produto | ✅ |
| DELETE | `/api/products/{id}` | Excluir produto | ✅ |

### Usuários

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| POST | `/api/users/register` | Registrar usuário | ❌ |
| GET | `/api/users/profile` | Perfil do usuário | ✅ |

### Health Check

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| GET | `/api/health` | Status da aplicação | ❌ |

## 🔍 Monitoramento

### Health Check

```bash
curl http://localhost:8080/api/health
```

### Logs

```bash
# Ver logs da aplicação
tail -f logs/application.log

# Ver logs do Docker
docker logs -f backend-produtos
```

## 🐛 Solução de Problemas

### Erro de conexão com banco

1. Verifique se o PostgreSQL está rodando
2. Confirme as credenciais no `application.yml`
3. Teste a conexão manualmente:

```bash
psql -h localhost -U produtos_user -d produtos_db
```

### Erro de JWT

1. Verifique se a variável `JWT_SECRET` está configurada
2. Confirme se o token não expirou
3. Teste com um novo login

### Problemas de build

```bash
# Limpar cache do Maven
mvn clean

# Reinstalar dependências
mvn dependency:purge-local-repository
mvn install
```

