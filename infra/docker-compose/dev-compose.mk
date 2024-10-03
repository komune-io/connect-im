DOCKER_COMPOSE_PATH = infra/docker-compose
DOCKER_COMPOSE_ENV = $(DOCKER_COMPOSE_PATH)/.env_dev
.PHONY: $(DOCKER_COMPOSE_FILE) $(ACTIONS) dev-help
ACTIONS = up down logs log pull stop kill help

include $(DOCKER_COMPOSE_ENV)
export

dev:
	$(eval ACTION := $(filter $(ACTIONS),$(MAKECMDGOALS)))
	$(eval SERVICE := $(filter $(DOCKER_COMPOSE_FILE),$(MAKECMDGOALS)))
	$(MAKE) --no-print-directory exec-common ACTION=$(ACTION) SERVICE=$(SERVICE) SERVICES_ALL="$(DOCKER_COMPOSE_FILE)"

dev-envsubst:
	mkdir -p $(DOCKER_COMPOSE_PATH)/config/build
	envsubst < $(DOCKER_COMPOSE_PATH)/config/init.json > $(DOCKER_COMPOSE_PATH)/config/build/init.json
	envsubst < $(DOCKER_COMPOSE_PATH)/config/space-create.json > $(DOCKER_COMPOSE_PATH)/config/build/space-create.json
	envsubst < $(DOCKER_COMPOSE_PATH)/config/space-config.json > $(DOCKER_COMPOSE_PATH)/config/build/space-config.json

exec-common:
	@if ! docker network ls | grep -q $(DOCKER_NETWORK); then \
		docker network create $(DOCKER_NETWORK); \
	fi
	@if [ "$(ACTION)" = "up" ]; then \
		$(MAKE) --no-print-directory dev-envsubst; \
	elif [ "$(ACTION)" = "down" ]; then \
		$(MAKE) --no-print-directory dev-envsubst-clean; \
	elif [ "$(ACTION)" = "kill" ]; then \
		$(MAKE) --no-print-directory kill-containers; \
	elif [ "$(ACTION)" = "help" ]; then \
		$(MAKE) --no-print-directory dev-help SERVICES_ALL="$(SERVICES_ALL)"; \
	fi
	@if [ "$(ACTION)" != "kill" ] && [ "$(ACTION)" != "help" ]; then \
		if [ "$(SERVICE)" = "" ]; then \
			for service in $(SERVICES_ALL); do \
				$(MAKE) --no-print-directory dev-service-action ACTION=$(ACTION) SERVICE=$$service; \
			done; \
		else \
			$(MAKE) --no-print-directory dev-service-action ACTION=$(ACTION) SERVICE=$(SERVICE); \
		fi; \
	fi

kill-containers:
	@if [ -n "$$(docker ps -q)" ]; then \
		CONTAINERS=$$(docker ps --format '{{.Names}}' | tr '\n' ',' | sed 's/,$$//; s/,/, /g'); \
		echo "Killing running containers: $$CONTAINERS"; \
		docker kill $$(docker ps -q); \
	else \
		echo "No running containers to kill."; \
	fi; \
	if [ -n "$$(docker ps -a -q)" ]; then \
		ALL_CONTAINERS=$$(docker ps -a --format '{{.Names}}' | tr '\n' ',' | sed 's/,$$//; s/,/, /g'); \
		echo "Removing containers: $$ALL_CONTAINERS"; \
		docker rm $$(docker ps -a -q); \
	else \
		echo "No containers to remove."; \
	fi


dev-service-action:
	@if [ "$(ACTION)" = "up" ]; then \
		docker compose --env-file $(DOCKER_COMPOSE_ENV) -f $(DOCKER_COMPOSE_PATH)/docker-compose-$(SERVICE).yml  up -d; \
	elif [ "$(ACTION)" = "stop" ]; then \
		docker compose --env-file $(DOCKER_COMPOSE_ENV) -f $(DOCKER_COMPOSE_PATH)/docker-compose-$(SERVICE).yml stop; \
	elif [ "$(ACTION)" = "down" ]; then \
		docker compose --env-file $(DOCKER_COMPOSE_ENV) -f $(DOCKER_COMPOSE_PATH)/docker-compose-$(SERVICE).yml down -v; \
	elif [ "$(ACTION)" = "logs" ]; then \
		docker compose --env-file $(DOCKER_COMPOSE_ENV) -f $(DOCKER_COMPOSE_PATH)/docker-compose-$(SERVICE).yml logs -f; \
	elif [ "$(ACTION)" = "log" ]; then \
		docker compose --env-file $(DOCKER_COMPOSE_ENV) -f $(DOCKER_COMPOSE_PATH)/docker-compose-$(SERVICE).yml logs; \
	elif [ "$(ACTION)" = "pull" ]; then \
		docker compose --env-file $(DOCKER_COMPOSE_ENV) -f $(DOCKER_COMPOSE_PATH)/docker-compose-$(SERVICE).yml pull; \
	else \
		echo 'No valid action: $(ACTION).'; \
		echo 'Available actions are:'; \
		echo '  up    - Start the service.'; \
		echo '  down  - Stop the service.'; \
		echo '  logs  - Show logs for the service.'; \
	fi

dev-envsubst-clean:
	rm -fr $(DOCKER_COMPOSE_PATH)/config/build

dev-help:
	@echo 'To operate on all services [$(SERVICES_ALL)]:'
	@echo '  make dev up    - Start all services.'
	@echo '  make dev down  - Stop all services.'
	@echo '  make dev logs  - Show logs for all services.'
	@echo ''
	@echo 'To operate on a specific service, use: make dev [service] [action]'
	@echo ''
	@echo 'Possible combinations are:'
	@$(foreach service,$(SERVICES_ALL), \
		echo ' Docker Compose file $(DOCKER_COMPOSE_PATH)/docker-compose-$(service).yml:'; \
		$(foreach action,$(ACTIONS), \
			echo '  make dev $(service) $(action)'; \
		) \
	)

$(DOCKER_COMPOSE_FILE):
	@:

$(DOCKER_COMPOSE_INIT_FILE):
	@:

$(ACTIONS):
	@:
