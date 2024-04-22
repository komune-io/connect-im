VERSION = $(shell cat VERSION)

.PHONY: lint build test publish promote

## New
lint:
	@make -f libs.mk lint
	@make -f docker.mk lint

build:
	@make -f libs.mk build
	@make -f docker.mk build

test:
	@make -f libs.mk test
	@make -f docker.mk test

publish:
	@make -f libs.mk publish
	@make -f docker.mk publish

promote:
	@make -f libs.mk promote
	@make -f docker.mk promote

## DOCKER-COMPOSE DEV ENVIRONMENT
include infra/docker-compose/dev-compose.mk
