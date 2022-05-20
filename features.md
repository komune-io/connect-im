# Keycloak features

## Requirements
Users:
- `Keyclaok Admin`: in the default realm `Master` --> credentials used to connect to the GUI
- `Realm Admin`: client created within the custom realm
- `Realm User`: user created within the custom realm
  
## Realm

### Create
*User: `Keyclaok Admin`*

```
i2-s2/realm/realm-f2-create
```

### Get all

```
TODO FXR-65 - As a Keyclaok Admin, I want to get Page of realm 
```

## Client
### Create first client of a realm
*User: `keyclaok Admin`*
*User: `Realm Admin`*

```
i2-s2/client/client-f2-create
```

### Update
*User: `Realm Admin`*

```
i2-s2/client/client-f2-update
```


### Generate new secret
*User: `Realm Admin`*
```
i2-s2/client/client-f2-generate-secret
```

### Get all realm clients
*User: `Realm Admin`*
```
i2-s2/client/client-f2-query
```

### Get realm client by id
*User: `Realm Admin`*
```
i2-s2/client/client-f2-query
```

## Role
Realm roles

### Create 
*User: `Realm Admin`*

```
i2-s2/role/role-f2-create
```

### Add role
*User: `Realm Admin`*

```
TODO FXR-69 - As Realm Admin, I want to add role
What is the differents betweem create and add?
```

### Add Composites
*User: `Realm Admin`*

```
TODO FXR-70 - As a Keyclaok Admin, I want to add Composites
```

### Get all roles

```
i2-s2/role/role-f2-query
```
### Get role by id

```
i2-s2/role/role-f2-query
```


## User
### Create
*User: `plateform-api`*

```
i2-s2/user/user-f2-create
```

### Update
*User: `plateform-api`*

```
i2-s2/user/user-f2-update
```


### Disable
*User: `plateform-api`*

```
i2-s2/user/user-f2-disable
```

### Delete
*User: `plateform-api`*

```
i2-s2/user/user-f2-delete
```


### Grant role
*User: `plateform-api`*

```
i2-s2/user/user-f2-roles-grant
```


### Revoke role
*User: `plateform-api`*

```
i2-s2/user/user-f2-roles-revoke
```

### Execute actions email
*User: `plateform-api`*

``` TODO test
i2-s2/user/user-f2-email-actions 
```


### Reset password
*User: `plateform-api`*

```
i2-s2/user/user-f2-password-reset
```

### Get all users
*User: `plateform-api`*

```
i2-s2/user/user-f2-query
```

### Get user by id
*User: `plateform-api`*

```
i2-s2/user/user-f2-query
```

### Get user by email
*User: `plateform-api`*

```
i2-s2/user/user-f2-query 
```


## Init

### As an keycloak admin I want init:
  * A realm
  * A client for the realm
  * Role with Permission
  * User

```
TODO FXR-83 - [Iint] As a Keyclaok Admin, I want to init a realm , client, role, user 
```