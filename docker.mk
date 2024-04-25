VERSION = $(shell cat VERSION)
DOCKER_REPOSITORY = ghcr.io/

include ./gradle.properties

IM_APP_NAME	   	 	:= im-gateway
IM_APP_IMG	    	:= ${IM_APP_NAME}:${VERSION}
IM_APP_PACKAGE	   	:= :im-api:api-gateway:bootBuildImage

IM_SCRIPT_NAME	   	:= im-script
IM_SCRIPT_IMG	    := ${IM_SCRIPT_NAME}:${VERSION}
IM_SCRIPT_PACKAGE	:= :im-script:im-script-gateway:bootBuildImage

KEYCLOAK_DOCKERFILE	:= infra/docker/keycloak/Dockerfile

KEYCLOAK_NAME	    := im-keycloak
KEYCLOAK_IMG        := ${KEYCLOAK_NAME}:${VERSION}

KEYCLOAK_AUTH_NAME	:= im-keycloak-auth
KEYCLOAK_AUTH_IMG   := ${KEYCLOAK_AUTH_NAME}:${VERSION}

.PHONY: lint build test publish promote

lint: docker-keycloak-lint
build: docker-im-gateway-build docker-script-build docker-keycloak-build docker-keycloak-auth-build

test:
	echo 'No Tests'

publish: docker-im-gateway-publish docker-script-publish docker-keycloak-publish docker-keycloak-auth-publish
promote: docker-im-gateway-promote docker-script-promote docker-keycloak-promote docker-keycloak-auth-promote

## im-gateway
docker-im-gateway-build:
	VERSION=${VERSION} ./gradlew build ${IM_APP_PACKAGE} --imageName ${IM_APP_IMG} -x test

docker-im-gateway-publish:
	@docker tag ${IM_APP_IMG} ghcr.io/komune-io/${IM_APP_IMG}
	@docker push ghcr.io/komune-io/${IM_APP_IMG}

docker-im-gateway-promote:
	@docker tag ${IM_APP_IMG} ghcr.io/komune-io/${IM_APP_IMG}
	@docker push docker.io/komune/${IM_APP_IMG}

## im-script
docker-script-build:
	VERSION=${VERSION} ./gradlew build ${IM_SCRIPT_PACKAGE} --imageName ${IM_SCRIPT_IMG} -x test

docker-script-publish:
	@docker tag ${IM_SCRIPT_IMG} ghcr.io/komune-io/${IM_SCRIPT_IMG}
	@docker push ghcr.io/komune-io/${IM_SCRIPT_IMG}

docker-script-promote:
	@docker tag ${IM_SCRIPT_IMG} ghcr.io/komune-io/${IM_SCRIPT_IMG}
	@docker push docker.io/komune/${IM_SCRIPT_IMG}

## Keycloak
docker-keycloak-lint:
	@docker run --rm -i hadolint/hadolint hadolint - < ${KEYCLOAK_DOCKERFILE}

docker-keycloak-build:
	./gradlew im-keycloak:keycloak-plugin:shadowJar
	@docker buildx build --platform linux/arm64,linux/amd64 --no-cache --build-arg KC_HTTP_RELATIVE_PATH=/  --build-arg KEYCLOAK_VERSION=${KEYCLOAK_VERSION} -f ${KEYCLOAK_DOCKERFILE} -t ${KEYCLOAK_IMG} .

docker-keycloak-publish:
	@docker tag ${KEYCLOAK_IMG} ghcr.io/komune-io/${KEYCLOAK_IMG}
	@docker push ghcr.io/komune-io/${KEYCLOAK_IMG}

docker-keycloak-promote:
	@docker tag ${KEYCLOAK_IMG} ghcr.io/komune-io/${KEYCLOAK_IMG}
	@docker push docker.io/komune/${KEYCLOAK_IMG}


# keycloak auth
docker-keycloak-auth-build:
	./gradlew im-keycloak:keycloak-plugin:shadowJar
	@docker buildx build --platform linux/arm64,linux/amd64 --no-cache --progress=plain --build-arg KC_HTTP_RELATIVE_PATH=/auth --build-arg KEYCLOAK_VERSION=${KEYCLOAK_VERSION} -f ${KEYCLOAK_DOCKERFILE} -t ${KEYCLOAK_AUTH_IMG} .

docker-keycloak-auth-publish:
	@docker tag ${KEYCLOAK_AUTH_IMG} ghcr.io/komune-io/${KEYCLOAK_AUTH_IMG}
	@docker push ghcr.io/komune-io/${KEYCLOAK_AUTH_IMG}

docker-keycloak-auth-promote:
	@docker tag ${KEYCLOAK_AUTH_IMG} ghcr.io/komune-io/${KEYCLOAK_AUTH_IMG}
	@docker push docker.io/komune/${KEYCLOAK_AUTH_IMG}
