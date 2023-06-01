#### Stage 1: Build the react application
FROM node:lts as build
ARG CI_NPM_AUTH_TOKEN
WORKDIR /app

COPY *.md ./
COPY storybook ./storybook

WORKDIR /app/storybook

RUN echo access=public \\n\
         @smartb:registry=https://gitlab.smartb.city/api/v4/projects/127/packages/npm/ \\n\
         //gitlab.smartb.city/api/v4/projects/127/packages/npm/:_authToken=${CI_NPM_AUTH_TOKEN} \
         > .npmrc

RUN yarn install
ENV PATH /app/storybook/node_modules/.bin:$PATH
RUN export SET NODE_OPTIONS=--openssl-legacy-provider && yarn build-storybook

FROM nginx:1.25.0-alpine

COPY --from=build /app/storybook/storybook-static /var/www
COPY infra/docker/storybook/nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

ENTRYPOINT ["nginx","-g","daemon off;"]
