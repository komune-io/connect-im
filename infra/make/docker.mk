VERSION = $(shell cat VERSION)
DOCKER_PLATFORM =  linux/arm64,linux/amd64

include ./gradle.properties
include infra/make/integration-tests.mk

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

# Declared once and shared by build, stage, promote and the local test build, so the image
# the integration suite runs against cannot drift from the one that ships. The relative
# path is the only intentional difference between the two variants.
KEYCLOAK_BUILD_ARGS      = --build-arg KC_HTTP_RELATIVE_PATH=/ --build-arg KEYCLOAK_VERSION=${KEYCLOAK_VERSION}
KEYCLOAK_AUTH_BUILD_ARGS = --build-arg KC_HTTP_RELATIVE_PATH=/auth --build-arg KEYCLOAK_VERSION=${KEYCLOAK_VERSION}

.PHONY: lint build test stage promote

lint: docker-keycloak-lint

build: docker-im-gateway-build docker-script-build docker-keycloak-build docker-keycloak-auth-build

# The suites that need a provisioned Keycloak run here rather than in libs.mk, because
# this make-file owns the images: `build` above has already produced ${KEYCLOAK_IMG} and
# ${IM_SCRIPT_IMG} in this runner, from this commit, with the same build args that ship.
#
# Running them from libs.mk would mean either provisioning from published images — which
# is how a Keycloak bump and a provisioning-script change both went untested — or building
# the images a second time from a duplicated recipe that can drift from the one above.
#
# DOCKER_REPOSITORY is emptied and VERSION_IM set to VERSION so compose resolves the local
# tags instead of docker.io. Both are passed as make command-line variables, which
# override what .env_dev supplies.
DEV_LOCAL_IMAGES = VERSION_IM=$(VERSION) DOCKER_REPOSITORY=

# im-script:${VERSION} already exists here — docker-script-build uses gradle's
# bootBuildImage, which does load into the local daemon (docker-script-stage merely tags
# it). Only the keycloak image needs the extra loadable build.
# im-init and im-config are one-shot containers and are ordered: im-config configures the
# realm that im-init creates. `docker compose up -d` returns as soon as a container starts,
# so bringing all three up together races them — im-config fails, and the previous
# arrangement papered over it by running `up` a second time and hoping the retry landed
# after im-init had finished.
#
# Each is now started and waited on explicitly. Waiting on the exit code also turns a
# provisioning failure into one readable error here instead of several dozen confusing
# test failures later.
test-pre: docker-keycloak-build-local
	@make dev keycloak up $(DEV_LOCAL_IMAGES)
	@make dev im-init up $(DEV_LOCAL_IMAGES)
	@docker wait im-init | grep -qx 0 || { docker logs im-init; echo 'im-init failed'; exit 1; }
	@make dev im-config up $(DEV_LOCAL_IMAGES)
	@docker wait im-config | grep -qx 0 || { docker logs im-config; echo 'im-config failed'; exit 1; }

test:
	sudo echo "127.0.0.1 im-keycloak" | sudo tee -a /etc/hosts
	./gradlew $(INTEGRATION_TESTS)

stage: docker-im-gateway-stage docker-script-stage docker-keycloak-stage docker-keycloak-auth-stage

promote: docker-im-gateway-promote docker-script-promote docker-keycloak-promote docker-keycloak-auth-promote

## im-gateway
docker-im-gateway-build:
	VERSION=${VERSION} ./gradlew build ${IM_APP_PACKAGE} -Dorg.gradle.parallel=true --imageName ${IM_APP_IMG} -x test

docker-im-gateway-stage:
	@docker tag ${IM_APP_IMG} ghcr.io/komune-io/${IM_APP_IMG}
	@docker push ghcr.io/komune-io/${IM_APP_IMG}

docker-im-gateway-promote:
	@docker tag ${IM_APP_IMG} docker.io/komune/${IM_APP_IMG}
	@docker push docker.io/komune/${IM_APP_IMG}

## im-script
docker-script-build:
	VERSION=${VERSION} ./gradlew build ${IM_SCRIPT_PACKAGE} -Dorg.gradle.parallel=true  --imageName ${IM_SCRIPT_IMG} -x test

docker-script-stage:
	@docker tag ${IM_SCRIPT_IMG} ghcr.io/komune-io/${IM_SCRIPT_IMG}
	@docker push ghcr.io/komune-io/${IM_SCRIPT_IMG}

docker-script-promote:
	@docker tag ${IM_SCRIPT_IMG} docker.io/komune/${IM_SCRIPT_IMG}
	@docker push docker.io/komune/${IM_SCRIPT_IMG}

## Keycloak
docker-keycloak-lint:
	@docker run --rm -i hadolint/hadolint hadolint - < ${KEYCLOAK_DOCKERFILE}

docker-keycloak-build:
	@echo 'Build ${KEYCLOAK_IMG}'
	./gradlew im-keycloak:keycloak-plugin:shadowJar
	@docker buildx build \
		--platform ${DOCKER_PLATFORM} \
		--no-cache $(KEYCLOAK_BUILD_ARGS) \
		-f ${KEYCLOAK_DOCKERFILE} \
		-t ${KEYCLOAK_IMG} .

# `docker-keycloak-build` above is multi-platform, and a multi-platform buildx build is
# never written to the local daemon — which is why `docker-keycloak-stage` rebuilds with
# --push rather than tagging, the way `docker-script-stage` does. compose therefore cannot
# run that image. This target produces the same image single-platform and --load'ed, so the
# integration suite has something to start. Same Dockerfile, same build args.
docker-keycloak-build-local:
	./gradlew im-keycloak:keycloak-plugin:shadowJar
	@docker buildx build --load \
		$(KEYCLOAK_BUILD_ARGS) \
		-f ${KEYCLOAK_DOCKERFILE} \
		-t ${KEYCLOAK_IMG} .

docker-keycloak-stage:
	@docker buildx build --push \
		--platform ${DOCKER_PLATFORM} \
		--no-cache $(KEYCLOAK_BUILD_ARGS) \
		-f ${KEYCLOAK_DOCKERFILE} \
		-t ghcr.io/komune-io/${KEYCLOAK_IMG} .

docker-keycloak-promote:
	@docker buildx build --push \
		--platform ${DOCKER_PLATFORM} \
		--no-cache $(KEYCLOAK_BUILD_ARGS) \
		-f ${KEYCLOAK_DOCKERFILE} \
		-t docker.io/komune/${KEYCLOAK_IMG} .


# keycloak auth
docker-keycloak-auth-build:
	@echo 'Build ${KEYCLOAK_AUTH_IMG}'
	./gradlew im-keycloak:keycloak-plugin:shadowJar
	@docker buildx build \
		--platform ${DOCKER_PLATFORM} \
		--no-cache $(KEYCLOAK_AUTH_BUILD_ARGS) \
		-f ${KEYCLOAK_DOCKERFILE} \
		-t ${KEYCLOAK_AUTH_IMG} .

docker-keycloak-auth-stage:
	@docker buildx build --push \
		--platform ${DOCKER_PLATFORM} \
		--no-cache $(KEYCLOAK_AUTH_BUILD_ARGS) \
		-f ${KEYCLOAK_DOCKERFILE} \
		-t ghcr.io/komune-io/${KEYCLOAK_AUTH_IMG} .

docker-keycloak-auth-promote:
	@docker buildx build --push \
		--platform ${DOCKER_PLATFORM} \
		--no-cache $(KEYCLOAK_AUTH_BUILD_ARGS) \
		-f ${KEYCLOAK_DOCKERFILE} \
		-t docker.io/komune/${KEYCLOAK_AUTH_IMG} .
