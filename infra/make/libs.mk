VERSION = $(shell cat VERSION)

.PHONY: clean lint build test stage promote

clean:
	./gradlew clean

lint:
	echo 'No Lint'

build:
	./gradlew build publishToMavenLocal -Dorg.gradle.parallel=true -x test

# Images built from this working tree, so the BDD suite exercises the commit under test.
# Without this the stack comes up from published images (see .env_dev) and any change to
# the Keycloak image or the provisioning scripts is invisible to CI.
test-images:
	./gradlew im-keycloak:keycloak-plugin:shadowJar
	docker build \
		--build-arg KC_HTTP_RELATIVE_PATH=/ \
		-f infra/docker/keycloak/Dockerfile \
		-t im-keycloak:$(VERSION) .
	VERSION=$(VERSION) ./gradlew :im-script:im-script-gateway:bootBuildImage \
		--imageName im-script:$(VERSION) -x test

# DOCKER_REPOSITORY is emptied so compose resolves the local tags rather than docker.io.
DEV_LOCAL_IMAGES = VERSION_IM=$(VERSION) DOCKER_REPOSITORY=

test-pre: test-images
	@make dev up $(DEV_LOCAL_IMAGES)
	@make dev im-init logs $(DEV_LOCAL_IMAGES)
	@make dev im-config logs $(DEV_LOCAL_IMAGES)
	@make dev up $(DEV_LOCAL_IMAGES)

test:
	sudo echo "127.0.0.1 im-keycloak" | sudo tee -a /etc/hosts
	./gradlew test

stage:
	VERSION=$(VERSION) ./gradlew stage -Dorg.gradle.parallel=true -x publishJsPackageToGithubRegistry -x publishJsPackageToNpmjsRegistry

#check:
	#./gradlew sonar -Dsonar.token=${SONAR_TOKEN} -Dorg.gradle.parallel=true

promote:
	VERSION=$(VERSION) ./gradlew promote -x publishJsPackageToGithubRegistry -x publishJsPackageToNpmjsRegistry
