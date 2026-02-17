# ============================================
# MAKEFILE - Student Management System
# ============================================
# Automatización de Docker para desarrollo

# Variables
DOCKER_COMPOSE = docker compose
ENV_FILE = --env-file .env
APP_NAME = student-management
CONTAINER_APP = student-management-app
CONTAINER_DB = student-management-db
BUILD_DATE = $(shell date -u +"%Y-%m-%dT%H:%M:%SZ")
VCS_REF = $(shell git rev-parse --short HEAD 2>/dev/null || echo "dev")

# Colores para mensajes
GREEN = \033[0;32m
YELLOW = \033[0;33m
RED = \033[0;31m
BLUE = \033[0;34m
CYAN = \033[0;36m
NC = \033[0m # No Color

# ============================================
# COMANDOS PRINCIPALES
# ============================================

.PHONY: help
help: ## Mostrar este mensaje de ayuda
	@echo "$(CYAN)========================================$(NC)"
	@echo "$(GREEN)  Student Management - Comandos Make$(NC)"
	@echo "$(CYAN)========================================$(NC)"
	@echo ""
	@echo "$(BLUE)Desarrollo Local:$(NC)"
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | grep -v "Producción\|Helper\|Base de Datos" | awk 'BEGIN {FS = ":.*?## "}; {printf "$(CYAN)%-20s$(NC) %s\n", $$1, $$2}'
	@echo ""
	@echo "$(YELLOW)Base de Datos:$(NC)"
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | grep "Base de Datos\|db-\|backup\|restore" | awk 'BEGIN {FS = ":.*?## "}; {printf "$(YELLOW)%-20s$(NC) %s\n", $$1, $$2}'
	@echo ""
	@echo "$(RED)Limpieza:$(NC)"
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | grep "Limpieza\|clean" | awk 'BEGIN {FS = ":.*?## "}; {printf "$(RED)%-20s$(NC) %s\n", $$1, $$2}'
	@echo ""

# ============================================
# DESARROLLO LOCAL
# ============================================

.PHONY: run
run: ## Ejecutar la aplicación localmente con Docker
	@echo "$(GREEN)🚀 Levantando aplicación...$(NC)"
	BUILD_DATE=$(BUILD_DATE) VCS_REF=$(VCS_REF) $(DOCKER_COMPOSE) up
	@echo "$(GREEN)✅ Aplicación corriendo!$(NC)"
	@echo "$(CYAN)🌐 URL Base: http://localhost:9090/student-management/api/v1$(NC)"
	@echo "$(CYAN)🔍 Health Check: http://localhost:9090/student-management/api/v1/actuator/health$(NC)"

.PHONY: run-build
run-build: ## Reconstruir imagen y ejecutar en modo detached (cambios en dependencias)
	@echo "$(GREEN)🔨 Reconstruyendo y levantando...$(NC)"
	BUILD_DATE=$(BUILD_DATE) VCS_REF=$(VCS_REF) $(DOCKER_COMPOSE) up -d --build
	@echo "$(GREEN)✅ Aplicación construida y corriendo (detached)$(NC)"
	@echo "$(CYAN)🌐 URL Base: http://localhost:9090/student-management/api/v1$(NC)"
	@echo "$(CYAN)🔍 Health Check: http://localhost:9090/student-management/api/v1/actuator/health$(NC)"

.PHONY: stop
stop: ## Detener y eliminar todos los contenedores
	@echo "$(YELLOW)🛑 Deteniendo contenedores...$(NC)"
	$(DOCKER_COMPOSE) down
	@echo "$(GREEN)✅ Contenedores detenidos$(NC)"

.PHONY: clean
clean: ## Detener y eliminar contenedores, volúmenes e imágenes (inicio limpio)
	@echo "$(RED)⚠️  ADVERTENCIA: Esto eliminará todos los datos$(NC)"
	@read -p "¿Estás seguro? [y/N]: " confirm && [ "$$confirm" = "y" ] || exit 1
	@echo "$(YELLOW)🧹 Limpiando recursos Docker...$(NC)"
	$(DOCKER_COMPOSE) down -v --rmi all
	@echo "$(GREEN)✅ Limpieza completa$(NC)"

.PHONY: restart
restart: ## Reiniciar todos los contenedores
	@echo "$(YELLOW)🔄 Reiniciando contenedores...$(NC)"
	$(DOCKER_COMPOSE) restart
	@echo "$(GREEN)✅ Contenedores reiniciados$(NC)"

.PHONY: restart-app
restart-app: ## Reiniciar solo la aplicación
	@echo "$(YELLOW)🔄 Reiniciando aplicación...$(NC)"
	$(DOCKER_COMPOSE) restart app
	@echo "$(GREEN)✅ Aplicación reiniciada$(NC)"

.PHONY: restart-db
restart-db: ## Reiniciar solo PostgreSQL
	@echo "$(YELLOW)🔄 Reiniciando base de datos...$(NC)"
	$(DOCKER_COMPOSE) restart postgres
	@echo "$(GREEN)✅ Base de datos reiniciada$(NC)"

.PHONY: debug
debug: ## Ejecutar la aplicación en modo debug
	@echo "$(BLUE)🐛 Iniciando en modo debug...$(NC)"
	$(DOCKER_COMPOSE) up

# ============================================
# LOGS Y MONITOREO
# ============================================

.PHONY: logs
logs: ## Ver logs de todos los servicios (seguimiento en tiempo real)
	$(DOCKER_COMPOSE) logs -f

.PHONY: logs-app
logs-app: ## Ver logs solo de la aplicación
	$(DOCKER_COMPOSE) logs -f app

.PHONY: logs-db
logs-db: ## Ver logs solo de PostgreSQL
	$(DOCKER_COMPOSE) logs -f postgres

.PHONY: logs-tail
logs-tail: ## Ver últimas 100 líneas de logs
	$(DOCKER_COMPOSE) logs --tail=100 -f

.PHONY: ps
ps: ## Ver estado de los contenedores
	@echo "$(BLUE)📊 Estado de contenedores:$(NC)"
	@$(DOCKER_COMPOSE) ps

.PHONY: stats
stats: ## Ver uso de recursos (CPU, memoria)
	@echo "$(BLUE)📈 Uso de recursos:$(NC)"
	@docker stats $(CONTAINER_APP) $(CONTAINER_DB)

.PHONY: health
health: ## Verificar salud de los servicios
	@echo "$(BLUE)🏥 Verificando salud de servicios...$(NC)"
	@curl -f http://localhost:9090/student-management/api/v1/actuator/health && echo "$(GREEN)✅ Aplicación funcionando$(NC)" || echo "$(RED)❌ Aplicación no responde$(NC)"
	@docker exec $(CONTAINER_DB) pg_isready -U postgres && echo "$(GREEN)✅ PostgreSQL funcionando$(NC)" || echo "$(RED)❌ PostgreSQL no responde$(NC)"

# ============================================
# ACCESO A CONTENEDORES
# ============================================

.PHONY: shell-app
shell-app: ## Acceder al shell del contenedor de la aplicación
	@echo "$(BLUE)🐚 Accediendo al contenedor de la aplicación...$(NC)"
	@docker exec -it $(CONTAINER_APP) sh

.PHONY: shell-db
shell-db: ## Acceder a PostgreSQL (psql) - Base de Datos
	@echo "$(BLUE)🐚 Accediendo a PostgreSQL...$(NC)"
	@docker exec -it $(CONTAINER_DB) psql -U postgres -d bd_docker_student_management_spring_boot_lta

# ============================================
# BASE DE DATOS
# ============================================

.PHONY: db-backup
db-backup: ## Crear backup de la base de datos - Base de Datos
	@echo "$(GREEN)💾 Creando backup...$(NC)"
	@mkdir -p backups
	@docker exec -t $(CONTAINER_DB) pg_dump -U postgres bd_docker_student_management_spring_boot_lta > backups/backup_$(shell date +%Y%m%d_%H%M%S).sql
	@echo "$(GREEN)✅ Backup creado en backups/$(NC)"

.PHONY: db-restore
db-restore: ## Restaurar último backup - Base de Datos
	@echo "$(YELLOW)📂 Restaurando último backup...$(NC)"
	@latest_backup=$$(ls -t backups/*.sql | head -1); \
	if [ -z "$$latest_backup" ]; then \
		echo "$(RED)❌ No se encontraron backups$(NC)"; \
		exit 1; \
	fi; \
	docker exec -i $(CONTAINER_DB) psql -U postgres bd_docker_student_management_spring_boot_lta < $$latest_backup
	@echo "$(GREEN)✅ Backup restaurado$(NC)"

.PHONY: db-reset
db-reset: ## Resetear base de datos (⚠️ BORRA DATOS) - Base de Datos
	@echo "$(RED)⚠️  ADVERTENCIA: Esto eliminará todos los datos de la BD$(NC)"
	@read -p "¿Estás seguro? [y/N]: " confirm && [ "$$confirm" = "y" ] || exit 1
	@echo "$(YELLOW)🗑️  Reseteando base de datos...$(NC)"
	@docker exec -it $(CONTAINER_DB) psql -U postgres -c "DROP DATABASE IF EXISTS bd_docker_student_management_spring_boot_lta;"
	@docker exec -it $(CONTAINER_DB) psql -U postgres -c "CREATE DATABASE bd_docker_student_management_spring_boot_lta;"
	@echo "$(GREEN)✅ Base de datos reseteada$(NC)"

# ============================================
# TESTING
# ============================================
# ============= TEST/JACOCO (en contenedor) ===========

.PHONY: maven-clean
maven-clean: ## Limpiar artefactos y carpetas generadas (Maven clean) dentro del contenedor
	@echo "$(YELLOW)🧹 Limpiando target y archivos generados del contenedor...$(NC)"
	@docker exec -it $(CONTAINER_APP) sh -c "mvn clean"
	@echo "$(GREEN)✅ Limpieza Maven en contenedor completada$(NC)"

.PHONY: test
test: ## Ejecutar suite de tests dentro del contenedor
	@echo "$(BLUE)🧪 Ejecutando tests...$(NC)"
	@docker exec -it $(CONTAINER_APP) sh -c "mvn test"
	@echo "$(GREEN)✅ Tests completados!$(NC)"

.PHONY: test-coverage
test-coverage: ## Ejecutar tests con reporte de cobertura
	@echo "$(BLUE)🧪 Ejecutando tests con cobertura...$(NC)"
	@docker exec -it $(CONTAINER_APP) sh -c "mvn test jacoco:report"
	@echo "$(GREEN)✅ Tests completados! Reporte en target/site/jacoco/index.html$(NC)"

.PHONY: test-unit
test-unit: ## Ejecutar solo tests unitarios
	@echo "$(BLUE)🧪 Ejecutando tests unitarios...$(NC)"
	@docker exec -it $(CONTAINER_APP) sh -c "mvn test -Dtest=*UnitTest"

.PHONY: test-integration
test-integration: ## Ejecutar solo tests de integración
	@echo "$(BLUE)🧪 Ejecutando tests de integración...$(NC)"
	@docker exec -it $(CONTAINER_APP) sh -c "mvn test -Dtest=*IntegrationTest"

.PHONY: test-coverage-check
test-coverage-check: ## Verificar cobertura mínima Jacoco (en contenedor)
	@echo "$(BLUE)🔍 Verificando cobertura mínima...$(NC)"
	@docker exec -it $(CONTAINER_APP) sh -c "mvn jacoco:check"

.PHONY: test-coverage-report
test-coverage-report: ## Generar solo el reporte de cobertura (en contenedor)
	@echo "$(CYAN)📊 Generando reporte Jacoco...$(NC)"
	@docker exec -it $(CONTAINER_APP) sh -c "mvn jacoco:report"

.PHONY: test-verify
test-verify: ## Limpiar, testear, verificar cobertura y paquete (en contenedor)
	@echo "$(BLUE)🔎 Ejecutando verificación completa...$(NC)"
	@docker exec -it $(CONTAINER_APP) sh -c "mvn clean verify"

# ============= TEST LOCAL OPCIONAL ==============
.PHONY: local-maven-clean
local-maven-clean: ## Limpiar artefactos y carpetas generadas (Maven clean) en local
	@echo "$(YELLOW)🧹 Limpiando target y archivos generados localmente...$(NC)"
	mvn clean
	@echo "$(GREEN)✅ Limpieza Maven en local completada$(NC)"

.PHONY: local-test
local-test: ## Ejecutar tests unitarios MAVEN en local
	@echo "$(BLUE)🧪 Ejecutando tests unitarios localmente...$(NC)"
	mvn clean test

.PHONY: local-coverage
local-coverage: ## Ejecutar tests y generar cobertura localmente
	@echo "$(BLUE)🧪 Ejecutando tests con cobertura (local)...$(NC)"
	mvn clean test jacoco:report
	@echo "$(GREEN)✅ Reporte Jacoco en: target/site/jacoco/index.html$(NC)"

.PHONY: local-coverage-check
local-coverage-check: ## Verificar cobertura mínima localmente
	@echo "$(BLUE)🔍 Verificando cobertura mínima (local)...$(NC)"
	mvn jacoco:check


# ============================================
# INFORMACIÓN Y UTILIDADES
# ============================================

.PHONY: info
info: ## Mostrar información del sistema
	@echo "$(CYAN)========================================$(NC)"
	@echo "$(GREEN)  Información del Sistema$(NC)"
	@echo "$(CYAN)========================================$(NC)"
	@echo "$(YELLOW)📦 Proyecto:$(NC) $(APP_NAME)"
	@echo "$(YELLOW)🐳 Docker Compose:$(NC) $(shell docker compose version 2>/dev/null || echo 'No instalado')"
	@echo "$(YELLOW)📅 Build Date:$(NC) $(BUILD_DATE)"
	@echo "$(YELLOW)🔖 VCS Ref:$(NC) $(VCS_REF)"
	@echo "$(YELLOW)🌐 URL:$(NC) http://localhost:9090/student-management/api/v1"
	@echo "$(YELLOW)🔍 Health:$(NC) http://localhost:9090/student-management/api/v1/actuator/health"
	@echo "$(YELLOW)🗄️  PostgreSQL:$(NC) localhost:5433"
	@echo ""

.PHONY: endpoints
endpoints: ## Listar endpoints disponibles
	@echo "$(CYAN)========================================$(NC)"
	@echo "$(GREEN)  Endpoints Disponibles$(NC)"
	@echo "$(CYAN)========================================$(NC)"
	@echo "$(YELLOW)Health:$(NC)       http://localhost:9090/student-management/api/v1/actuator/health"
	@echo "$(YELLOW)Info:$(NC)         http://localhost:9090/student-management/api/v1/actuator/info"
	@echo "$(YELLOW)Students:$(NC)     http://localhost:9090/student-management/api/v1/students/listStudents"
	@echo "$(YELLOW)PostgreSQL:$(NC)   localhost:5433 (usuario: postgres)"
	@echo ""

.PHONY: open
open: ## Abrir la aplicación en el navegador
	@echo "$(GREEN)🌐 Abriendo navegador...$(NC)"
	@if command -v xdg-open > /dev/null 2>&1; then \
		xdg-open http://localhost:9090/student-management/api/v1/actuator/health; \
	elif command -v open > /dev/null 2>&1; then \
		open http://localhost:9090/student-management/api/v1/actuator/health; \
	else \
		echo "$(YELLOW)Abre manualmente: http://localhost:9090/student-management/api/v1/actuator/health$(NC)"; \
	fi

# ============================================
# LIMPIEZA AVANZADA
# ============================================

.PHONY: clean-all
clean-all: ## Limpieza profunda (incluye imágenes huérfanas) - Limpieza
	@echo "$(RED)⚠️  ADVERTENCIA: Limpieza profunda del sistema$(NC)"
	@read -p "¿Estás seguro? [y/N]: " confirm && [ "$$confirm" = "y" ] || exit 1
	@echo "$(YELLOW)🧹 Limpieza profunda...$(NC)"
	$(DOCKER_COMPOSE) down -v --rmi all --remove-orphans
	docker system prune -f
	@echo "$(GREEN)✅ Limpieza profunda completa$(NC)"

.PHONY: clean-volumes
clean-volumes: ## Eliminar solo volúmenes (mantiene imágenes) - Limpieza
	@echo "$(RED)⚠️  ADVERTENCIA: Esto eliminará todos los datos de la BD$(NC)"
	@read -p "¿Estás seguro? [y/N]: " confirm && [ "$$confirm" = "y" ] || exit 1
	@echo "$(YELLOW)🧹 Eliminando volúmenes...$(NC)"
	$(DOCKER_COMPOSE) down -v
	@echo "$(GREEN)✅ Volúmenes eliminados$(NC)"

# Default target
.DEFAULT_GOAL := help