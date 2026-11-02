VERSION = $(shell cat VERSION)

.PHONY: clean lint build test-pre test test-integration stage promote

clean:
	@make -f infra/make/libs.mk clean

lint:
	@make -f infra/make/libs.mk lint
	@make -f infra/make/docker.mk lint

build:
	@make -f infra/make/libs.mk build
	@make -f infra/make/docker.mk build

# Provisions Keycloak from images built from this checkout. In CI, docker.mk's own `build`
# has already produced im-script; locally it has to be built here first.
test-pre:
	@make -f infra/make/docker.mk docker-script-build test-pre

# Unit tests only; the Keycloak-dependent suites are `test-integration`, after `test-pre`.
test:
	@make -f infra/make/libs.mk test

test-integration:
	@make -f infra/make/docker.mk test

stage:
	@make -f infra/make/libs.mk stage
	@make -f infra/make/docker.mk stage
ifeq ($(findstring SNAPSHOT,$(VERSION)),)
	@make -f infra/make/libsJs.mk stage
endif

promote:
	@make -f infra/make/libs.mk promote
	@make -f infra/make/docker.mk promote
ifeq ($(findstring SNAPSHOT,$(VERSION)),)
	@make -f infra/make/libsJs.mk promote
endif

## DOCKER-COMPOSE DEV ENVIRONMENT
include infra/docker-compose/dev-compose.mk
