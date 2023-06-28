DOCKER_COMPOSE_FILE = keycloak realm-init realm-config

DOCKER_COMPOSE_PATH = infra/docker-compose
DOCKER_COMPOSE_ENV = $(DOCKER_COMPOSE_PATH)/.env_dev
.PHONY: $(DOCKER_COMPOSE_FILE)
ACTIONS = up down logs

dev:
	$(eval ACTION := $(filter $(ACTIONS),$(MAKECMDGOALS)))
	$(eval SERVICE := $(filter $(DOCKER_COMPOSE_FILE),$(MAKECMDGOALS)))
	@if [ "$(SERVICE)" = "" ]; then \
		for service in $(DOCKER_COMPOSE_FILE); do \
			$(MAKE) --no-print-directory dev-service-action ACTION=$(ACTION) SERVICE=$$service; \
		done; \
	else \
		$(MAKE) --no-print-directory dev-service-action ACTION=$(ACTION) SERVICE=$(SERVICE); \
	fi

dev-service-action:
	@if [ "$(ACTION)" = "up" ]; then \
		docker compose --env-file $(DOCKER_COMPOSE_ENV) -f $(DOCKER_COMPOSE_PATH)/docker-compose-$(SERVICE).yml  up -d; \
	elif [ "$(ACTION)" = "down" ]; then \
		docker compose --env-file $(DOCKER_COMPOSE_ENV) -f $(DOCKER_COMPOSE_PATH)/docker-compose-$(SERVICE).yml down -v --remove-orphans; \
	elif [ "$(ACTION)" = "logs" ]; then \
		docker compose --env-file $(DOCKER_COMPOSE_ENV) -f $(DOCKER_COMPOSE_PATH)/docker-compose-$(SERVICE).yml logs -f; \
	else \
		echo 'No valid action: $(ACTION).'; \
		echo 'Available actions are:'; \
		echo '  up    - Start the service.'; \
		echo '  down  - Stop the service.'; \
		echo '  logs  - Show logs for the service.'; \
	fi

dev-help:
	@echo 'To operate on all services [$(DOCKER_COMPOSE_FILE)]:'
	@echo '  make dev up    - Start all services.'
	@echo '  make dev down  - Stop all services.'
	@echo '  make dev logs  - Show logs for all services.'
	@echo ''
	@echo 'To operate on a specific service, use: make dev [service] [action]'
	@echo ''
	@echo 'Possible combinations are:'
	@$(foreach service,$(DOCKER_COMPOSE_FILE), \
		echo ' Docker Compose file $(DOCKER_COMPOSE_PATH)/docker-compose-$(service).yml:'; \
		$(foreach action,$(ACTIONS), \
			echo '  make dev $(service) $(action)'; \
		) \
	)

$(DOCKER_COMPOSE_FILE):
	@:

$(ACTIONS):
	@:
