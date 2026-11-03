# IM

## Description
IM is a microservice for Identity and Access Management built with Kotlin and Spring Cloud Function, leveraging a Keycloak instance. It enables the creation and management of Spaces, Users, Organizations, and Privileges, which can then be used to authenticate via Keycloak.

## Table of Contents
1. [Overview](#overview)
2. [Architecture](#architecture)
3. [Configuration](#configuration)
    - [Application Configuration](#application-configuration)
    - [Docker-Compose Configuration](#docker-compose-configuration)
4. [Features and Functionalities](#features-and-functionalities)
    - [Users](#users)
    - [Organizations](#organizations)
    - [Privileges](#privileges)
        - [Features](#features)
        - [Roles](#roles)
        - [Permissions](#permissions)
5. [Getting Started](#getting-started)
6. [Testing](#testing)
7. [Troubleshooting](#troubleshooting)
8. [Keycloak Integration](#keycloak-integration)
    - [Keycloak Plugin](#keycloak-plugin)
    - [IM-Event-HTTP](#im-event-http)

## Overview

IM simplifies identity and access management with the following features:
- User Management: Handle individual identities, roles, and permissions.
- Organization Management: Group users and assign collective roles and privileges.
- Privileges: Manage granular permissions, roles, and access control.

## Architecture

IM interacts with Keycloak using [Keycloak's Admin Client](https://mvnrepository.com/artifact/org.keycloak/keycloak-admin-client) and exposes endpoints to communicate with other applications.

![IM Architecture]()

## Configuration

### Application Configuration

Define the necessary parameters in your `application.yml` file:

```yaml
connect:
    fs:
        url: http://fs:8090
```

### Docker-Compose Configuration

A sample `docker-compose` configuration for the IM Gateway:

```yaml
services:
    im-gateway:
    image: ${DOCKER_REPOSITORY}im-gateway:${VERSION_IM}
    environment:
        - server_port=8004
        - f2_tenant_issuer-base-uri=${KC_URL_PUBLIC}
        - connect_im_keycloak_url=${KC_URL_PUBLIC}
        - connect_im_keycloak_realm=${KC_ROOT_REALM}
        - connect_im_keycloak_clientId=${KC_IM_CLIENT_ID}
        - connect_im_keycloak_clientSecret=${KC_IM_CLIENT_SECRET}
    ports:
      - "8004:8004"
```

## Features and Functionalities

### Users
- Represent individual identities with specific roles and permissions.
- Can belong to one or more organizations.
- Authenticate through Keycloak, operating within assigned permissions.

### Organizations
- Represent collective entities grouping multiple users.
- Have roles that define overarching permissions and features.
- Govern actions available to users within the organization.

### Privileges

#### Features
Features act as access control filters, defining the specific contexts in which permissions can be applied. They ensure permissions are valid only in allowed contexts.

#### Roles
- Roles group permissions for users or organizations.
- They simplify access management and can define bindings to other roles or users.

#### Permissions

| Permission Name                 | Description                               | Required Features        |
|---------------------------------|-------------------------------------------|--------------------------|
| **im_user_read**                | Ability to view any user data             | feat_im_all, feat_im_own |
| **im_user_write**               | Ability to modify any user data           | feat_im_all, feat_im_own |
| **im_user_role_write**          | Ability to modify the roles of a user     | feat_im_all, feat_im_own |
| **im_user_self**                | Ability to view and modify one's own account (profile, email, password, MFA) | feat_im_all, feat_im_own |
| **im_organization_read**        | Ability to view any organization data     | feat_im_all              |
| **im_organization_write**       | Ability to modify any organization data   | feat_im_all              |
| **im_organization_read_own**    | Ability to view own organization data and its members | feat_im_all, feat_im_own |
| **im_organization_write_own**   | Ability to modify own organization data   | feat_im_all, feat_im_own |
| **im_organization_apikey_read** | Ability to read any organization API keys | feat_im_all              |
| **im_organization_status_write**| Ability to write organization status      | feat_im_all              |
| **im_role_read**                | Ability to view any role data             | feat_im_all, feat_im_own |
| **im_role_write**               | Ability to modify any role data           | feat_im_all              |
| **im_apikey_read**              | Ability to view API keys                  | feat_im_all, feat_im_own |
| **im_apikey_write**             | Ability to modify API keys                | feat_im_all, feat_im_own |
| **im_space_read**               | Ability to view space data                | feat_im_all              |
| **im_space_write**              | Ability to modify space data              | feat_im_all              |
| **im_mfa_force_otp**                | Ability to force MFA otp on a user        | feat_im_all              |

#### Default permissions

Every rule of IM requires a permission, acting on oneself included: reading or modifying
one's own account needs `im_user_self`, reading one's own organization, its members or the
organization and user refs needs `im_organization_read_own` (or `im_organization_read` /
`im_user_read`).

Both are granted to every user by default, as composites of the realm's default role
(`default-roles-<realm>`). Keycloak gives that role to every user it creates, through the
admin API or self-registration, and to every client's service account (API keys included);
IM keeps it when it reassigns a user's roles. Putting them on a user or admin role is
therefore unnecessary, and would be lost each time IM rewrites that user's roles.

Like every permission carrying `features`, they reach a token only when the client's scope
holds `feat_im_all` or `feat_im_own` (see `UserRealmRoleFeatureMapper`): a full-scope client
gets them, a client without full scope — a third-party application acting for the user —
does not, and its token can neither read nor modify its own user or organization. A user
whose roles hold neither feature gets neither permission, as for every other `feat_im_own`
permission.

**New realms**: `im-script` (`im.script.space.jsonCreate`) creates the permissions and adds
them to the default role.

**Existing realms**: re-run `im-script` with the realm's `space-create.json` as
`im.script.space.jsonCreate`. The realm is kept as is; the script creates the IM permissions
that are missing and adds those two to the default role if it does not hold them yet.
Without that re-run, users of the realm lose access to their own account and organization
through IM. If `im-script` cannot be run, do the same with `kcadm.sh`:

```bash
REALM=my-realm
kcadm.sh config credentials --server "$KC_URL" --realm master --user "$KC_ROOT_USER" --password "$KC_ROOT_PASS"
kcadm.sh get "realms/$REALM" --fields defaultRole   # usually default-roles-$REALM
kcadm.sh create roles -r "$REALM" -s name=im_user_self \
  -s "description=Ability to view and modify one's own account (profile, email, password, MFA)" \
  -s 'attributes={"type":["PERMISSION"],"features":["[\"feat_im_all\"]","[\"feat_im_own\"]"]}'
kcadm.sh create roles -r "$REALM" -s name=im_organization_read_own \
  -s "description=Ability to view own organization data and its members" \
  -s 'attributes={"type":["PERMISSION"],"features":["[\"feat_im_all\"]","[\"feat_im_own\"]"]}'
kcadm.sh add-roles -r "$REALM" --rname "default-roles-$REALM" \
  --rolename im_user_self --rolename im_organization_read_own
```

## Getting Started

### SDKs
Use the following dependencies in your Kotlin project to manage Users, Organizations, and Roles:

```yaml
implementation("io.komune.im:user-domain:${Versions.im}")
implementation("io.komune.im:organization-domain:${Versions.im}")
implementation("io.komune.im:role-domain:${Versions.im}")
```

### Clients
The clients provided use Ktor and should be singletons:

```kotlin
@Configuration
class ImConfig(
private val tokenProvider: TokenProvider
) {
@Value("\${im.url}")
lateinit var imUrl: String

    @Bean
    fun userClient() = UserClient(
        url = imUrl,
        generateBearerToken = tokenProvider::getToken
    )
}
```


# Keycloak Plugin

## Create a plugin

1. Create a module inside `i2-keycloak:keycloak-plugin`
2. Build it with `./gradlew i2-keycloak:keycloak-plugin:shadowJar`
3. Build the Dockerfile located in `i2-keycloak/docker/`
4. Enjoy

(steps 2 and 3 can be done automatically with `make package-keycloak`)

## Welcome email

`PUT /admin/realms/{realm}/im-welcome-email/users/{id}` sends `welcome.ftl` with the subject `welcomeSubject`, `manage-users` required. IM calls it for a creation handing over the password, and for a password reset on an account without credentials. The link lasts `connect.im.user.welcomeLinkLifespanSeconds` (7 days by default).

To use it, an email theme adds `html/welcome.ftl`, `text/welcome.ftl` and the `welcomeSubject` message. A theme without them keeps Keycloak's `executeActions` mail with the realm's lifespan. Re-read the endpoint against Keycloak's `UserResource.executeActionsEmail` at every Keycloak upgrade.

## I2-Event-HTTP

client ("xxx-web" + "account") hardcoded claim: `event-http-wehbook` = url to send the events to
> if local and keycloak is within docker container, use ip address instead of localhost

It is possible to filter the events to send from the keycloak GUI by going to Events > Login Events Settings > Save Events ON > Saved Types

Endpoint example:
```kotlin
@Configuration
class KeycloakEventEndpoint(
    private val applicationContext: ApplicationContext
) {

    @PermitAll
    @Bean
    fun keycloakEvent(): F2Consumer<KeycloakHttpEvent> = f2Consumer { event ->
        println(event.type)
        applicationContext.publishEvent(event)
    }
}
```
