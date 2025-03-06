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
| **im_organization_read**        | Ability to view any organization data     | feat_im_all              |
| **im_organization_write**       | Ability to modify any organization data   | feat_im_all              |
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
