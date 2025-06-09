# Projeto DevOps - Conteinerização de Aplicação Java e Oracle Database

Este projeto demonstra a conteinerização de uma aplicação Java (Spring Boot) e um banco de dados Oracle XE utilizando Docker e Docker Compose, seguindo os requisitos do trabalho de DevOps.

## Estrutura do Projeto

```
.
├── GsJava-main/             # Código-fonte da aplicação Java
│   ├── pom.xml
│   ├── src/
│   └── target/              # Contém o JAR compilado da aplicação
├── oracle-db/               # Diretório para o Dockerfile do Oracle Database
│   └── Dockerfile
├── java-app/                # Diretório para o Dockerfile da Aplicação Java
│   ├── Dockerfile
│   └── alertachuva-api-0.0.1-SNAPSHOT.jar # JAR da aplicação copiado para o contexto do Docker
├── docker-compose.yml       # Arquivo de orquestração Docker Compose
└── README.md                # Este arquivo
```

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

- **Docker Desktop** (ou Docker Engine e Docker Compose)
- **Maven** (para compilar o projeto Java, se necessário)
- **Java Development Kit (JDK) 17** (para compilar o projeto Java, se necessário)

## Configuração do Projeto

### 1. Aplicação Java (`GsJava-main`)

O projeto Java foi configurado para se conectar a um banco de dados Oracle. As configurações de conexão estão no arquivo `GsJava-main/src/main/resources/application.properties`:

```properties
# Configuração do Oracle
spring.datasource.url=jdbc:oracle:thin:@oracle-db:1521/ORCL
spring.datasource.driverClassName=oracle.jdbc.OracleDriver
spring.datasource.username=RM555962
spring.datasource.password=191105
```

**Observação**: A senha do banco de dados (`191105`) e o usuário (`RM555962`) são fornecidos diretamente no `application.properties` e no `docker-compose.yml`. Em um ambiente de produção, é altamente recomendável usar variáveis de ambiente ou um sistema de gerenciamento de segredos para credenciais sensíveis.

### 2. Dockerfile do Oracle Database (`oracle-db/Dockerfile`)

Este Dockerfile cria uma imagem personalizada do Oracle XE, incluindo um usuário não-root (`oracleuser`) e um diretório de trabalho (`/opt/oracle/app`), conforme as regras do trabalho.

```dockerfile
FROM gvenzl/oracle-xe:latest

USER root

RUN useradd -ms /bin/bash oracleuser && \
    mkdir -p /opt/oracle/app && \
    chown -R oracleuser:oracleuser /opt/oracle/app

USER oracleuser
WORKDIR /opt/oracle/app

EXPOSE 1521

ENV ORACLE_SID=ORCL
```

### 3. Dockerfile da Aplicação Java (`java-app/Dockerfile`)

Este Dockerfile cria a imagem da aplicação Java, utilizando OpenJDK 17, copiando o JAR compilado e expondo a porta 8080.

```dockerfile
FROM openjdk:17-jdk-slim

RUN useradd -ms /bin/bash appuser && \
    mkdir -p /app && \
    chown -R appuser:appuser /app

USER appuser
WORKDIR /app

COPY alertachuva-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 4. Docker Compose (`docker-compose.yml`)

O arquivo `docker-compose.yml` orquestra os dois containers, configurando a rede, volumes e variáveis de ambiente para a comunicação.

```yaml
version: "3.8"

services:
  oracle-db:
    image: my-oracle-db:latest
    container_name: oracle-db
    ports:
      - "1521:1521"
    volumes:
      - oracle_data:/opt/oracle/oradata
    environment:
      - ORACLE_PWD=admin # Senha para o usuário SYS, SYSTEM e PDBADMIN
      - ORACLE_SID=ORCL
    networks:
      - app-network

  java-app:
    image: my-java-app:latest
    container_name: java-app
    ports:
      - "8080:8080"
    depends_on:
      - oracle-db
    environment:
      - SPRING_DATASOURCE_URL=jdbc:oracle:thin:@oracle-db:1521/ORCL
      - SPRING_DATASOURCE_USERNAME=RM555962
      - SPRING_DATASOURCE_PASSWORD=191105
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update
    networks:
      - app-network

volumes:
  oracle_data:

networks:
  app-network:
    driver: bridge
```

## Como Rodar o Projeto

Siga os passos abaixo para construir as imagens e iniciar os containers:

### 1. Compilar a Aplicação Java (se ainda não o fez)

Navegue até o diretório `GsJava-main` e compile o projeto:

```bash
cd GsJava-main
mvn clean install
cd ..
```

Após a compilação, o arquivo `alertachuva-api-0.0.1-SNAPSHOT.jar` estará em `GsJava-main/target/`. Certifique-se de que este JAR seja copiado para o diretório `java-app/` antes de construir a imagem Docker da aplicação Java.

```bash
cp GsJava-main/target/alertachuva-api-0.0.1-SNAPSHOT.jar java-app/
```

### 2. Construir as Imagens Docker

Navegue até os respectivos diretórios e construa as imagens:

```bash
# Construir a imagem do Oracle Database
cd oracle-db
docker build -t my-oracle-db:latest .
cd ..

# Construir a imagem da Aplicação Java
cd java-app
docker build -t my-java-app:latest .
cd ..
```

### 3. Iniciar os Containers com Docker Compose

No diretório raiz do projeto (onde está o `docker-compose.yml`), execute:

```bash
docker compose up -d
```

Este comando iniciará ambos os containers em segundo plano.

### 4. Verificar os Logs

Para verificar se os containers iniciaram corretamente e para monitorar a saída, use:

```bash
docker compose logs -f
```

Procure por mensagens que indiquem que o Oracle Database está pronto para conexões e que a aplicação Spring Boot foi iniciada na porta 8080.

### 5. Testar a Aplicação

Com os containers em execução, a API Java estará acessível em `http://localhost:8080`. Você pode usar ferramentas como Postman, Insomnia ou `curl` para testar as operações CRUD. Por exemplo:

- **GET** `http://localhost:8080/alertas`
- **POST** `http://localhost:8080/alertas` (com um corpo JSON apropriado)

Certifique-se de demonstrar todas as operações CRUD e a persistência dos dados no banco de dados Oracle.

## Requisitos do Trabalho Atendidos

- **Conteinerização da API Java**: `java-app/Dockerfile` e `my-java-app:latest`.
- **Conteinerização do Banco de Dados Oracle**: `oracle-db/Dockerfile` e `my-oracle-db:latest`.
- **Dois containers Docker integrados**: `docker-compose.yml`.
- **Dockerfile para Banco de Dados (obrigatório)**: `oracle-db/Dockerfile`.
- **Usuário não root no container do DB**: `oracleuser` no `oracle-db/Dockerfile`.
- **Diretório de trabalho no container do DB**: `/opt/oracle/app` no `oracle-db/Dockerfile`.
- **Variável de ambiente no container do DB**: `ORACLE_SID` no `oracle-db/Dockerfile` e `ORACLE_PWD` no `docker-compose.yml`.
- **Expor porta para acesso à aplicação**: Porta 8080 no `java-app/Dockerfile` e mapeada no `docker-compose.yml`.
- **CRUD completo e persistência**: A aplicação Java deve implementar isso, e a conexão com o Oracle está configurada.
- **Volume nomeado para persistência do DB**: `oracle_data` no `docker-compose.yml`.
- **Variável de ambiente no container da aplicação**: `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `SPRING_JPA_HIBERNATE_DDL_AUTO` no `docker-compose.yml`.
- **Porta exposta para acesso externo ao DB**: Porta 1521 no `oracle-db/Dockerfile` e mapeada no `docker-compose.yml`.
- **Containers em modo background**: `docker compose up -d`.
- **Exibir logs**: `docker compose logs -f`.
- **Repositório com tudo que é necessário**: Este `README.md`, os Dockerfiles, o `docker-compose.yml` e o código-fonte da aplicação Java.

