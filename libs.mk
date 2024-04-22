VERSION = $(shell cat VERSION)

.PHONY: lint build test publish promote

lint:
	echo 'No Lint'
build:
	./gradlew build publishToMavenLocal -x test
test:
	echo 'No Tests'
publish:
	VERSION=$(VERSION) PKG_MAVEN_REPO=github ./gradlew publish --info

promote:
	VERSION=$(VERSION) PKG_MAVEN_REPO=sonatype_oss ./gradlew publish
