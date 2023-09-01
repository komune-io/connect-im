include ./gradle.properties

STORYBOOK_DOCKERFILE	:= infra/docker/storybook/Dockerfile
STORYBOOK_NAME	   	 	:= smartbcity/im-storybook
STORYBOOK_IMG	    	:= ${STORYBOOK_NAME}:${VERSION}

IM_APP_NAME	   	 	:= smartbcity/im-gateway
IM_APP_IMG	    	:= ${IM_APP_NAME}:${VERSION}
IM_APP_PACKAGE	   	:= :im-api:api-gateway:bootBuildImage

IM_SCRIPT_NAME	   	:= smartbcity/im-script
IM_SCRIPT_IMG	    := ${IM_SCRIPT_NAME}:${VERSION}
IM_SCRIPT_PACKAGE	:= :im-script:script-gateway:bootBuildImage

KEYCLOAK_DOCKERFILE	:= infra/docker/keycloak/Dockerfile

KEYCLOAK_NAME	    := smartbcity/im-keycloak
KEYCLOAK_IMG        := ${KEYCLOAK_NAME}:${VERSION}

KEYCLOAK_AUTH_NAME	:= smartbcity/im-keycloak-auth
KEYCLOAK_AUTH_IMG   := ${KEYCLOAK_AUTH_NAME}:${VERSION}

libs: package-kotlin
docker: docker-build docker-push
docs: docs-build docs-push

docker-build: docker-im-gateway-build docker-script-build docker-keycloak-build docker-keycloak-auth-build
docker-push: docker-im-gateway-push docker-script-push docker-keycloak-push docker-keycloak-auth-push

docs-build: package-im-storybook-build
docs-push: package-im-storybook-push

package-kotlin:
	@gradle clean build publish -x test --stacktrace

docker-im-gateway-build:
	VERSION=${VERSION} ./gradlew build ${IM_APP_PACKAGE} -x test --stacktrace

docker-im-gateway-push:
	@docker push ${IM_APP_IMG}

package-im-storybook-build:
	@docker build --build-arg CI_NPM_AUTH_TOKEN=${CI_NPM_AUTH_TOKEN} -f ${STORYBOOK_DOCKERFILE} -t ${STORYBOOK_IMG} .

package-im-storybook-push:
	@docker push ${STORYBOOK_IMG}

## Keycloak
docker-script-build:
	VERSION=${VERSION} ./gradlew build ${IM_SCRIPT_PACKAGE} -x test --stacktrace

docker-script-push:
	@docker push ${IM_SCRIPT_IMG}

## Keycloak
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


help:
	@echo '/////////////////////////////////'
	@echo 'Build tasks:'
	@echo 'Usage: make [TARGET]'
	@echo 'Targets:'
	@echo '  libs: Build kotlin libraries'
	@echo '  docker: Build and push docker images'
	@echo '  docs: Build and push docs'
	@echo ''
	@echo '/////////////////////////////////'
	@echo 'Dev Environment tasks: make dev-help'
	@make -s dev-help

## DOCKER-COMPOSE DEV ENVIRONMENT
include infra/docker-compose/dev-compose.mk
