#### Stage 1: Build the react application
FROM node:21.7-alpine3.19 as build

WORKDIR /app

COPY *.md ./
COPY storybook ./storybook

WORKDIR /app/storybook

ARG FIXERS_PUBLISH_NPM_GITHUB_TOKEN
RUN printf "\
@komune-io:registry=https://npm.pkg.github.com\n\
//npm.pkg.github.com/:_authToken=%s\n\
" "${FIXERS_PUBLISH_NPM_GITHUB_TOKEN}" > .npmrc


RUN if [ ! -d "./storybook-static" ]; then \
      yarn install --frozen-lockfile --ignore-scripts && \
      yarn build-storybook; \
    fi

FROM nginx:1.29.8-alpine

LABEL org.opencontainers.image.source="https://github.com/komune-io/connect-im"

COPY --from=build /app/storybook/storybook-static /var/www
COPY infra/docker/storybook/nginx.conf /etc/nginx/nginx.conf

RUN chown -R nginx:nginx /var/cache/nginx && \
    chown -R nginx:nginx /var/log/nginx && \
    chown -R nginx:nginx /etc/nginx/conf.d

RUN touch /var/run/nginx.pid && \
        chown -R nginx:nginx /var/run/nginx.pid
USER nginx


EXPOSE 80

ENTRYPOINT ["nginx","-g","daemon off;"]
