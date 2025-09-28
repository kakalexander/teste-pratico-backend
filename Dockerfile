FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar o arquivo pom.xml
COPY pom.xml .

# Baixar dependências (cache layer)
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copiar código fonte
COPY src ./src

# Compilar a aplicação
RUN mvn clean package -DskipTests

# Expor a porta
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "target/teste-pratico-api-0.0.1-SNAPSHOT.jar"]
