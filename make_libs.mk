VERSION = $(shell cat VERSION)

.PHONY: lint build test stage promote

lint:
	echo 'No Lint'

build:
	./gradlew build publishToMavenLocal -Dorg.gradle.parallel=true -x test

test-pre:
	@make dev up
	@make dev im-init logs
	@make dev im-config logs
	@make dev up

test:
	sudo echo "127.0.0.1 im-keycloak" | sudo tee -a /etc/hosts
	./gradlew test

stage:
	VERSION=$(VERSION) ./gradlew stage -Dorg.gradle.parallel=true -x publishJsPackageToGithubRegistry -x publishJsPackageToNpmjsRegistry

check:
	./gradlew sonar -Dsonar.token=${SONAR_TOKEN} -Dorg.gradle.parallel=true

promote:
	VERSION=$(VERSION) ./gradlew promote -x publishJsPackageToGithubRegistry -x publishJsPackageToNpmjsRegistry
