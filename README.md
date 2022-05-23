# IM

# Description

IM is a micro-service for Identity and Access Management. It is built upon a Keycloak instance.

It allows you to create and manage: Users, Organizations and Roles. These Users and Organizations can then be used to authenticate to a Keycloak instance.

# Architecture

IM interacts with Keycloak using [Keycloak’s Admin Client](https://mvnrepository.com/artifact/org.keycloak/keycloak-admin-client). IM also exposes entrypoints to allow communications with any applications.

<img src="https://docs.smartb.city/s3/docs/im/diagrams/architecture.png" alt="drawing" width="300"/>

# Getting started

IM works with a running Keycloak instance. It can easily be deployed as a docker container, within a docker-compose file: ([link to docker repo](https://hub.docker.com/r/smartbcity/i2-gateway))

```yaml
version: "3.7"
services:
  im-gateway:
    image: smartbcity/im-gateway:${VERSION}
    container_name: im-gateway
    environment:
      server_port: 8004
      keycloak_auth-server-url: ${KEYCLOAK_URL}      
      keycloak_realm: ${KEYCLOAK_REALM}
      keycloak_clients_admin_realm: ${IM_REALM}
      keycloak_clients_admin_clientId: ${IM_CLIENT_ID}
      keycloak_clients_admin_clientSecret: ${IM_CLIENT_SECRET}
    ports:
      - "8004:8004"
```

Functionalities are divided into 3 entities: User, Organization and Role.

If you are in a java environment all the necessary models and commands for an entity can be found here:

- [User SDK](https://mvnrepository.com/artifact/city.smartb.im/user-domain)
- [Organization SDK](https://mvnrepository.com/artifact/city.smartb.im/organization-domain)
- [Role SDK](https://mvnrepository.com/artifact/city.smartb.im/role-domain)

```kotlin
implementation("city.smartb.im:user-domain:${Versions.im}")
implementation("city.smartb.im:organization-domain:${Versions.im}")
implementation("city.smartb.im:role-domain:${Versions.im}")
```

SDK that sugarcoats the http requests are also available:
- [User Client](https://mvnrepository.com/artifact/city.smartb.im/user-client)
- [OrganizationClient](https://mvnrepository.com/artifact/city.smartb.im/organization-client)
- [Role Client](https://mvnrepository.com/artifact/city.smartb.im/role-client)

```kotlin
implementation("city.smartb.im:user-client:${Versions.im}")
implementation("city.smartb.im:organization-client:${Versions.im}")
implementation("city.smartb.im:role-client:${Versions.im}")
```

The client provided in the SDK uses Ktor under the hood and should be a singleton in your application in order to prevent any memory leak. Ex:

```kotlin
@Configuration
class ImConfig {

    @Value("\${platform.im.url}")
    lateinit var imUrl: String

    @Bean
    fun userClient() = UserClient(
        url = imUrl
    )

    @Bean
    fun organizationClient() = OrganizationClient(
        url = imUrl
    )

    @Bean
    fun roleClient() = RoleClient(
        url = imUrl
    )
}
```

# Endpoints & Role

## Organizations

| Endpoint | Associated role       |
| --- |-----------------------|
| organizationCreate | super_admin           |
| organizationUpdate | im_write_organization |
| organizationGet | im_read_organization  |
| organizationGetBySiret | im_read_organization  |
| organizationPage | im_read_organization  |
| organizationRefGetAll | im_read_organization  |

| Roles                 | Endpoints |
|-----------------------| --- |
| im_write_organization | organizationUpdate |
| im_read_organization  | organizationGet, organizationGetBySiret, organizationPage, organizationRefGetAll |
| admin                 | * |

## Users

| Endpoints | Associated role |
| --- |--------------|
| userCreate | im_write_user |
| userUpdate | im_write_user |
| userResetPassword | im_write_user |
| userGet | im_read_user |
| userPage | im_read_user |

| Roles         | Endpoints |
|---------------|---|
| im_write_user | userCreate, userUpdate, userResetPassword |
| im_read_user  | userGet, userPage |
| admin         | * |

## Roles

| Endpoints | Associated role |
| --- |-----------------|
| roleAddComposites | super_admin     |
| roleCreate | super_admin     |
| roleUpdate | super_admin     |

| Roles       | Endpoints |
|-------------| --- |
| super_admin | * |

# Configuration

Properties prefix: `keycloak`

| Property | Description | Example | Default |
| --- | --- | --- | --- |
| auth-server-url | URL to a keycloak server | https://auth.smart-b.io/auth | https://auth.tracabois.smart-b.io/auth |
| realm | Realm to interact with | development | test |
| clients.admin.realm | Realm to authenticate to | development | test |
| clients.admin.clientId | Client ID used to authenticate | smartclient | i2-api |
| clients.admin.clientSecret | Client Secret used to authenticate | smartsecret | xxxxx |

# Errors