#### Stage 1: Build the react application
FROM node:lts as build

WORKDIR /app

COPY *.md ./
COPY storybook ./storybook

WORKDIR /app/storybook

ARG CI_NPM_AUTH_TOKEN
RUN echo -e "\
access=public \n\
 @komune-io:registry=https://npm.pkg.github.com/komune-io \n\
//npm.pkg.github.com/:_authToken=${CI_NPM_AUTH_TOKEN} \n\
" > .npmrc

RUN yarn install
ENV PATH /app/storybook/node_modules/.bin:$PATH
RUN export SET NODE_OPTIONS=--openssl-legacy-provider && yarn build-storybook

FROM nginx:1.25.0-alpine

COPY --from=build /app/storybook/storybook-static /var/www
COPY infra/docker/storybook/nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

ENTRYPOINT ["nginx","-g","daemon off;"]
