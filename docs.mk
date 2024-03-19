DOCKER_REPOSITORY = ghcr.io/

STORYBOOK_DOCKERFILE	:= ./infra/docker/storybook/Dockerfile
STORYBOOK_NAME	   	 	:= ${DOCKER_REPOSITORY}komune-io/fs-storybook
STORYBOOK_IMG	    	:= ${STORYBOOK_NAME}:${VERSION}
STORYBOOK_LATEST		:= ${STORYBOOK_NAME}:latest


lint: lint-docker-storybook

build: build-storybook

test:
	echo 'No Test'

package: package-storybook push-storybook

# Storybook
build-storybook:
	@yarn --cwd storybook install --frozen-lockfile --ignore-scripts
	@yarn --cwd storybook build-storybook

lint-docker-storybook:
	@docker run --rm -i hadolint/hadolint hadolint - < ${STORYBOOK_DOCKERFILE}

package-storybook:
	@docker build --no-cache  --platform=linux/amd64 \
		--build-arg NPM_AUTH_TOKEN=${NPM_AUTH_TOKEN} \
		-f ${STORYBOOK_DOCKERFILE} \
		-t ${STORYBOOK_IMG} .

push-storybook:
	@docker push ${STORYBOOK_IMG}
