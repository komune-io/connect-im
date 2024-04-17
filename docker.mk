VERSION = $(shell cat VERSION)
DOCKER_REPOSITORY = ghcr.io/

include ./gradle.properties

IM_APP_NAME	   	 	:= ${DOCKER_REPOSITORY}komune-io/im-gateway
IM_APP_IMG	    	:= ${IM_APP_NAME}:${VERSION}
IM_APP_PACKAGE	   	:= :im-api:api-gateway:bootBuildImage

IM_SCRIPT_NAME	   	:= ${DOCKER_REPOSITORY}komune-io/im-script
IM_SCRIPT_IMG	    := ${IM_SCRIPT_NAME}:${VERSION}
IM_SCRIPT_PACKAGE	:= :im-script:im-script-gateway:bootBuildImage

KEYCLOAK_DOCKERFILE	:= infra/docker/keycloak/Dockerfile

KEYCLOAK_NAME	    := ${DOCKER_REPOSITORY}komune-io/im-keycloak
KEYCLOAK_IMG        := ${KEYCLOAK_NAME}:${VERSION}

KEYCLOAK_AUTH_NAME	:= ${DOCKER_REPOSITORY}komune-io/im-keycloak-auth
KEYCLOAK_AUTH_IMG   := ${KEYCLOAK_AUTH_NAME}:${VERSION}

lint: docker-keycloak-lint

build: docker-im-gateway-build docker-script-build docker-keycloak-build docker-keycloak-auth-build

test:
	echo 'No Tests'

publish: docker-im-gateway-push docker-script-push docker-keycloak-push docker-keycloak-auth-push

promote:
	echo 'No Tests'

## im-gateway
docker-im-gateway-build:
	VERSION=${VERSION} ./gradlew build ${IM_APP_PACKAGE} --imageName ${IM_APP_IMG} -x test

docker-im-gateway-push:
	VERSION=${VERSION} docker push ${IM_APP_IMG}

## im-script
docker-script-build:
	VERSION=${VERSION} ./gradlew build ${IM_SCRIPT_PACKAGE} --imageName ${IM_SCRIPT_IMG} -x test

docker-script-push:
	@docker push ${IM_SCRIPT_IMG}

## Keycloak
docker-keycloak-lint:
	@docker run --rm -i hadolint/hadolint hadolint - < ${KEYCLOAK_DOCKERFILE}

docker-keycloak-build:
	./gradlew im-keycloak:keycloak-plugin:shadowJar
	@docker build --no-cache --build-arg KC_HTTP_RELATIVE_PATH=/  --build-arg KEYCLOAK_VERSION=${KEYCLOAK_VERSION} -f ${KEYCLOAK_DOCKERFILE} -t ${KEYCLOAK_IMG} .

docker-keycloak-push:
	@docker push ${KEYCLOAK_IMG}

docker-keycloak-auth-build:
	./gradlew im-keycloak:keycloak-plugin:shadowJar
	@docker build --no-cache --progress=plain --build-arg KC_HTTP_RELATIVE_PATH=/auth --build-arg KEYCLOAK_VERSION=${KEYCLOAK_VERSION} -f ${KEYCLOAK_DOCKERFILE} -t ${KEYCLOAK_AUTH_IMG} .

docker-keycloak-auth-push:
	@docker push ${KEYCLOAK_AUTH_IMG}

## New
version:
	@VERSION=$$(cat VERSION); \
	echo "$$VERSION"

## DOCKER-COMPOSE DEV ENVIRONMENT
include infra/docker-compose/dev-compose.mk
