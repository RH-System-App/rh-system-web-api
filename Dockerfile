# Estágio 1: Build da Aplicação (Builder)
# Usamos uma imagem com o JDK completo para compilar o código fonte
FROM eclipse-temurin:17-jdk-jammy AS builder

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos do wrapper do Maven para aproveitar o cache de layers do Docker
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Baixa todas as dependências do projeto. Se o pom.xml não mudar, o Docker usará o cache desta camada
RUN ./mvnw dependency:go-offline

# Copia o código-fonte da aplicação
COPY src ./src

# Compila a aplicação e gera o arquivo .jar, pulando os testes
RUN ./mvnw package -DskipTests

# ---

# Estágio 2: Imagem Final (Runtime)
# Usamos uma imagem JRE (Java Runtime Environment), que é muito menor que o JDK
FROM eclipse-temurin:17-jre-jammy

# Define o diretório de trabalho
WORKDIR /app

# Cria um usuário não-root para executar a aplicação (boa prática de segurança)
RUN useradd -ms /bin/bash springuser
USER springuser

# Copia apenas o .jar gerado do estágio de build para a imagem final
# A sintaxe --from=builder é o que conecta os estágios
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação quando o container for executado
ENTRYPOINT ["java", "-jar", "app.jar"]