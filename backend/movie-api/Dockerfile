####
# Development Dockerfile for Quarkus Movie API
# Runs in Quarkus dev mode with hot reload, Dev UI, and debugging support
####

FROM gradle:8.11-jdk17

WORKDIR /app

# Copy gradle wrapper and configuration files
# (source code will be mounted as volumes for hot reload)
COPY gradle ./gradle
COPY gradlew ./
COPY gradlew.bat ./
COPY gradle.properties ./
COPY settings.gradle ./
COPY build.gradle ./

# Pre-download dependencies for faster startup
RUN gradle dependencies --no-daemon || true

# Copy source code (will be overridden by volume mounts in docker-compose)
COPY src ./src

# Expose application port and debug port
EXPOSE 8080 5005

# Run Quarkus in development mode
# Features: Dev UI at /q/dev, hot reload, continuous testing
CMD ["./gradlew", "quarkusDev", "-Dquarkus.http.host=0.0.0.0"]
