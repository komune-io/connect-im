VERSION = $(shell cat VERSION)

include ./gradle.properties

## New
lint: lint-libs
build: build-libs
test: test-libs
publish: publish-libs
promote: promote-libs

lint-libs:
	echo 'No Lint'
	#./gradlew detekt

build-libs:
	./gradlew build publishToMavenLocal -x test

test-libs:
	echo 'No Tests'
#	./gradlew test

publish-libs:
	VERSION=$(VERSION) PKG_MAVEN_REPO=github ./gradlew publish --info

promote-libs:
	VERSION=$(VERSION) PKG_MAVEN_REPO=sonatype_oss ./gradlew publish

version:
	@VERSION=$$(cat VERSION); \
	echo "$$VERSION"

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
