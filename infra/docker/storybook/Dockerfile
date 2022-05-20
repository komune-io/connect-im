#### Stage 1: Build the react application
FROM node:12.4.0 as build

WORKDIR /app

COPY *.md ./
COPY .npmrc ./
COPY storybook ./storybook

RUN  apt update && apt-get install -y gconf-service libasound2 libatk1.0-0 libc6 libcairo2 libcups2 libdbus-1-3 libexpat1 libfontconfig1 libgcc1 libgconf-2-4 libgdk-pixbuf2.0-0 libglib2.0-0 libgtk-3-0 libnspr4 libpango-1.0-0 libpangocairo-1.0-0 libstdc++6 libx11-6 libx11-xcb1 libxcb1 libxcomposite1 libxcursor1 libxdamage1 libxext6 libxfixes3 libxi6 libxrandr2 libxrender1 libxss1 libxtst6 ca-certificates fonts-liberation libappindicator1 libnss3 lsb-release xdg-utils wget

WORKDIR /app/storybook

RUN yarn install
ENV PATH /app/storybook/node_modules/.bin:$PATH
RUN yarn build-storybook
RUN npx sb extract

FROM nginx:1.17.0-alpine

COPY --from=build /app/storybook/storybook-static /var/www
COPY infra/docker/storybook/nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

ENTRYPOINT ["nginx","-g","daemon off;"]
