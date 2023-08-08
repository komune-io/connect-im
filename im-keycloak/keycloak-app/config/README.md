## Authentication config

Set keycloak authentication env variables in docker-compose file

- i2_keycloak_auth-server-url=
- i2_keycloak_realm=
- i2_keycloak_client-id=
- i2_keycloak_client-secret=


## Realm config

There are two ways to configure keycloak:

### Use env var in docker compose:
* Web client
  * Client Id (i2_config_web-client_client-id)
  * Web Url (i2_config_web-client_web-url)
  * Localhost Url (i2_config_web-client_localhost-url)
* App client
  * Client Id (i2_config_app-client_client-id)

### Json file: (see example ./test.json)
* Web client
    * Client Id
    * Base Url
    * Localhost Url
* App client
    * Client Id
* Roles
* Composite roles
* Users
  * username
  * email
  * firstname
  * lastname
  * role
  * password (optional)
