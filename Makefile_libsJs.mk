VERSION = $(shell cat VERSION)

.PHONY: lint build test stage promote

lint:
	echo 'No Lint'

build:
	VERSION=${VERSION} ./gradlew :im-f2:apikey:im-apikey-domain:build
	VERSION=${VERSION} ./gradlew :im-f2:organization:im-organization-domain:build
	VERSION=${VERSION} ./gradlew :im-f2:privilege:im-privilege-domain:build
	VERSION=${VERSION} ./gradlew :im-f2:space:im-space-domain:build
	VERSION=${VERSION} ./gradlew :im-f2:user:im-user-domain:build

test:
	echo 'No Tests'

stage:
	VERSION=${VERSION} ./gradlew :im-f2:apikey:im-apikey-domain:publishJsPackageToGithubRegistry
	VERSION=${VERSION} ./gradlew :im-f2:organization:im-organization-domain:publishJsPackageToGithubRegistry
	VERSION=${VERSION} ./gradlew :im-f2:privilege:im-privilege-domain:publishJsPackageToGithubRegistry
	VERSION=${VERSION} ./gradlew :im-f2:space:im-space-domain:publishJsPackageToGithubRegistry
	VERSION=${VERSION} ./gradlew :im-f2:user:im-user-domain:publishJsPackageToGithubRegistry

promote:
	VERSION=${VERSION} ./gradlew :im-f2:apikey:im-apikey-domain:publishJsPackageToNpmjsRegistry
	VERSION=${VERSION} ./gradlew :im-f2:organization:im-organization-domain:publishJsPackageToNpmjsRegistry
	VERSION=${VERSION} ./gradlew :im-f2:privilege:im-privilege-domain:publishJsPackageToNpmjsRegistry
	VERSION=${VERSION} ./gradlew :im-f2:space:im-space-domain:publishJsPackageToNpmjsRegistry
	VERSION=${VERSION} ./gradlew :im-f2:user:im-user-domain:publishJsPackageToNpmjsRegistry
