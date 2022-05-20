# I2 (gateway)

# Description

I2 is a micro-service for Identity and Access Management. It is built upon a Keycloak instance.

It allows you to create and manage: Users, Organizations and Roles. These Users and Organizations can then be used to authenticate to a Keycloak instance.

# Architecture

I2 interacts with Keycloak using [Keycloak’s Admin Client](https://mvnrepository.com/artifact/org.keycloak/keycloak-admin-client). I2 also exposes entrypoints to allow communications with any applications.

<img src="https://docs.smartb.city/s3/docs/im/diagrams/architecture.png" alt="drawing" width="300"/>

# Getting started

I2 works with a running Keycloak instance. It can easily be deployed as a docker container, within a docker-compose file: ([link to docker repo](https://hub.docker.com/r/smartbcity/i2-gateway))

```yaml
version: "3.7"
services:
  i2-gateway:
    image: smartbcity/i2-gateway:${VERSION}
    container_name: i2-gateway
    environment:
      server_port: 8004
      keycloak_auth-server-url: ${KEYCLOAK_URL}      
      keycloak_realm: ${KEYCLOAK_REALM}
      keycloak_clients_admin_realm: ${I2_REALM}
      keycloak_clients_admin_clientId: ${I2_CLIENT_ID}
      keycloak_clients_admin_clientSecret: ${I2_CLIENT_SECRET}
    ports:
      - "8004:8004"
```

Functionalities are divided into 3 entities: User, Organization and Role.

If you are in a java environment all the necessary models and commands for an entity can be found here:

- [User SDK](https://mvnrepository.com/artifact/city.smartb.i2/i2-user-domain)
- [Organization SDK](https://mvnrepository.com/artifact/city.smartb.i2/organization-domain)
- [Role SDK](https://mvnrepository.com/artifact/city.smartb.i2/i2-role-domain)

```kotlin
implementation("city.smartb.i2:i2-user-domain:${Versions.i2}")
implementation("city.smartb.i2:organization-domain:${Versions.i2}")
implementation("city.smartb.i2:i2-role-domain:${Versions.i2}")
```

An SDK that sugarcoats the http requests is also available [here](https://mvnrepository.com/artifact/city.smartb.i2/i2-client).

```kotlin
implementation("city.smartb.i2:i2-client:${Versions.i2}")
```

The client provided in the SDK uses Ktor under the hood and should be a singleton in your application in order to prevent any memory leak. Ex:

```kotlin
@Configuration
class I2Config {

    @Value("\${platform.i2.url}")
    lateinit var i2Url: String

    @Bean
    fun i2Client() = I2Client(
        url = i2Url
    )
}
```

# Endpoints & Role

## Organizations

| Endpoint | Associated role |
| --- | --- |
| organizationCreate | admin |
| organizationUpdate | admin, i2_write_organization |
| organizationGet | admin, i2_read_organization |
| organizationGetBySiret | admin, i2_read_organization |
| organizationPage | admin, i2_read_organization |
| organizationRefGetAll | admin, i2_read_organization |

| Roles | Endpoints |
| --- | --- |
| i2_write_organization | organizationUpdate |
| i2_read_organization | organizationGet, organizationGetBySiret, organizationPage, organizationRefGetAll |
| admin | * |

## Users

| Endpoints | Associated role |
| --- | --- |
| userCreate | admin, i2_write_user |
| userUpdate | admin, i2_write_user |
| userResetPassword | admin, i2_write_user |
| userGet | admin, i2_read_user |
| userPage | admin, i2_read_user |

| Roles | Endpoints |
| --- |---|
| i2_write_user | userCreate, userUpdate, userResetPassword |
| i2_read_user | userGet, userPage |
| admin | * |

## Roles

| Endpoints | Associated role |
| --- | --- |
| roleAddComposites | admin |
| roleCreate | admin |
| roleUpdate | admin |

| Roles | Endpoints |
| --- | --- |
| admin | * |

# Configuration (TODO update default value)

Properties prefix: `keycloak`

| Property | Description | Example | Default |
| --- | --- | --- | --- |
| auth-server-url | URL to a keycloak server | https://auth.smart-b.io/auth | https://auth.tracabois.smart-b.io/auth |
| realm | Realm to interact with | development | test |
| clients.admin.realm | Realm to authenticate to | development | test |
| clients.admin.clientId | Client ID used to authenticate | smartclient | i2-api |
| clients.admin.clientSecret | Client Secret used to authenticate | smartsecret | xxxxx |

# Errors