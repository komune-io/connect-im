ARG KEYCLOAK_VERSION

FROM quay.io/keycloak/keycloak:26.0.0 AS builder
ENV KC_DB=postgres

ENV KC_LOG_LEVEL=INFO

ARG KC_HTTP_RELATIVE_PATH
RUN echo "KC_HTTP_RELATIVE_PATH=${KC_HTTP_RELATIVE_PATH}"
ENV KC_HTTP_RELATIVE_PATH=${KC_HTTP_RELATIVE_PATH}

COPY im-keycloak/keycloak-plugin/build/libs/keycloak-plugin-with-dependencies.jar /opt/keycloak/providers/im-event-http.jar
RUN /opt/keycloak/bin/kc.sh build --db=postgres

FROM quay.io/keycloak/keycloak:26.0.0
COPY --from=builder /opt/keycloak/ /opt/keycloak/
WORKDIR /opt/keycloak

ENV KC_DB=postgres

ENV KC_PROXY=none
ENV KC_HTTP_ENABLED=true
ENV KC_HOSTNAME_STRICT=false
ENV KC_HOSTNAME=localhost

COPY infra/docker/keycloak/config/themes/im /opt/keycloak/themes/im/

ENTRYPOINT ["/opt/keycloak/bin/kc.sh","--spi-login-protocol-openid-connect-legacy-logout-redirect-uri=true", "start", "--optimized"]
