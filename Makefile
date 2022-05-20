STORYBOOK_DOCKERFILE	:= infra/docker/storybook/Dockerfile
STORYBOOK_NAME	   	 	:= smartbcity/im-storybook
STORYBOOK_IMG	    	:= ${STORYBOOK_NAME}:${VERSION}

IM_APP_NAME	   	 	:= smartbcity/im-gateway
IM_APP_IMG	    	:= ${IM_APP_NAME}:${VERSION}
IM_APP_PACKAGE	   	:= :im-api:api-gateway:bootBuildImage

libs: package-kotlin
docker: package-app
docs: package-storybook

package-kotlin:
	@gradle clean build publish -x test --stacktrace

package-storybook:
	@docker build -f ${STORYBOOK_DOCKERFILE} -t ${STORYBOOK_IMG} .
	@docker push ${STORYBOOK_IMG}

package-app:
	VERSION=${VERSION} ./gradlew build ${IM_APP_PACKAGE} -x test --stacktrace
	@docker push ${IM_APP_IMG}
