# Estágio 1: Build
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /home/gradle/src
# Copia apenas os arquivos de configuração primeiro para aproveitar o cache de dependências
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

# Dá permissão de execução e baixa as dependências
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

# Copia o código fonte e builda de fato
COPY src src
# Tenta o build genérico se o buildFatJar falhar
RUN ./gradlew build -x test --no-daemon

# Estágio 2: Execução
FROM eclipse-temurin:17-jre-jammy
EXPOSE 8080
WORKDIR /app
# O comando 'build' do Gradle coloca os JARs em build/libs
# Vamos copiar o jar que termina com "-all.jar" ou o maior arquivo da pasta
COPY --from=build /home/gradle/src/build/libs/*-all.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]