VERSION = $(shell cat VERSION)

include infra/make/integration-tests.mk

.PHONY: clean lint build test stage promote

clean:
	./gradlew clean

lint:
	echo 'No Lint'

build:
	./gradlew build publishToMavenLocal -Dorg.gradle.parallel=true -x test

# Unit tests only: no Docker, no Keycloak, no /etc/hosts entry. The suites that need a
# provisioned Keycloak run from docker.mk, where the images they need have just been
# built — see integration-tests.mk.
test:
	./gradlew test $(addprefix -x ,$(INTEGRATION_TESTS))

stage:
	VERSION=$(VERSION) ./gradlew stage -Dorg.gradle.parallel=true -x publishJsPackageToGithubRegistry -x publishJsPackageToNpmjsRegistry

#check:
	#./gradlew sonar -Dsonar.token=${SONAR_TOKEN} -Dorg.gradle.parallel=true

promote:
	VERSION=$(VERSION) ./gradlew promote -x publishJsPackageToGithubRegistry -x publishJsPackageToNpmjsRegistry
