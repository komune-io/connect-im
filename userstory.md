# Space 

As a super 

# Script

## Init
Can be used by a root axess to create init space

```json
[
  {
    "realmId": "${KC_REALM}",
    "theme": "${KC_REALM_THEME}",
    "smtp": {
      "host": "${SMTP_HOST}",
      "port": "${SMTP_POST}",
      "from": "noreply@komune.io",
      "ssl": "false",
      "starttls": "false",
      "auth": "false"
    },
    "adminUser": [{
      "username": "${KC_ADMIN_USER}",
      "password": "${KC_ADMIN_PASS}",
      "email": "admin@admin.com",
      "firstName": "admin",
      "lastName": "im"
    }],
    "adminClient": [{
      "clientId": "${KC_ADMIN_CLIENT_ID}",
      "clientSecret": "${KC_ADMIN_CLIENT_SECRET}"
    }],
    "baseRoles": [
      "super_admin",
      "im_read_user",
      "im_write_user",
      "im_write_organization",
      "im_read_organization",
      "im_read_role",
      "im_write_role",
      "im_write_my_organization",
      "admin",
      "user"
    ]
  }
]
```

## User story

## Config

```json
{
  "appClients": [
    {
      "clientId": "${KC_ADMIN_CLIENT_ID}",
      "clientSecret": "${KC_ADMIN_CLIENT_SECRET}",
      "roles": ["super_admin"],
      "realmManagementRoles": [
        "manage-users",
        "view-users",
        "query-groups",
        "manage-realm"
      ]
    }
  ],
  "webClients": [
    {
      "clientId": "im-test-web",
      "webUrl": "http://localhost:3000"
    }
  ],
  "users": [],
  "roles": [
    "im_read_user",
    "im_write_user",
    "im_write_organization",
    "im_read_organization",
    "im_read_role",
    "im_write_role",
    "im_write_my_organization",
    "im_read_apikey",
    "im_write_apikey",

    "super_admin",
    "admin",
    "user"
  ],
  "roleComposites": {
  }
}

```