# ============================================
# ETAPA 1: CONSTRUCCIÓN
# ============================================
# Primera etapa: imagen Base (Construcción del jar)
FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivo pom.xml y descargar (cachear) las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Compilar aplicación (pasa el codigo completo del proyecto)
COPY src ./src
RUN mvn clean package -DskipTests # Compila el codigo y crea el archivo .jar

# Verificar que el JAR se creó
RUN ls -la /app/target

# ============================================
# ETAPA 2: IMAGEN FINAL
# ============================================
# Segunda etapa: Construcción imagen final (es una distribución de linux para poder ejecutar la app)
FROM eclipse-temurin:21-jdk-alpine

# Instalar curl para healthcheck
RUN apk add --no-cache curl

# Argumentos de build para metadata dinámica
ARG BUILD_DATE
ARG VCS_REF
ARG VERSION=0.0.1-SNAPSHOT

# Metadata completa siguiendo estándares OCI
LABEL maintainer="SaulHernandoEcheverri" \
      version="${VERSION}" \
      description="Student Management System with Spring Boot" \
      org.opencontainers.image.title="student-management" \
      org.opencontainers.image.version="${VERSION}" \
      org.opencontainers.image.authors="SaulHernandoEcheverri" \
      org.opencontainers.image.vendor="LTA" \
      org.opencontainers.image.licenses="Apache-2.0" \
      org.opencontainers.image.url="https://github.com/saulolo/student-management" \
      org.opencontainers.image.documentation="https://github.com/saulolo/student-management/README.md" \
      org.opencontainers.image.created="${BUILD_DATE}" \
      org.opencontainers.image.revision="${VCS_REF}" \
      tech.stack="Java 21, Spring Boot 4.0.1, PostgreSQL, Thymeleaf"

# Establecer directorio de trabajo
WORKDIR /app

# Copiar JAR desde la etapa de construcción
COPY --from=builder /app/target/*.jar app.jar

# Puerto de la aplicación
EXPOSE 9090

# Configuración de memoria JVM
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Healthcheck (requiere curl y Spring Boot Actuator)
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:9090/student-management/api/v1/actuator/health || exit 1

# Ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
