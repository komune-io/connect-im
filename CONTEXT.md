# connect-im — Context

IM is Komune's **Identity Management service**: multi-tenant users, organizations, API keys, MFA, and a Privilege model (Roles → Permissions → Features) layered on Keycloak. Exposes its capabilities as [F2 functions](../../fixers/fixers-f2/CONTEXT.md); persists state in Keycloak + Redis.

## Glossary

### Space

The **tenant**. A Space owns users, organizations, branding (displayName, theme), SMTP, locales, and a Keycloak realm. `SpaceIdentifier` reuses Keycloak's `RealmId` as its implementation key, but the concepts are not interchangeable: Space is the tenant-policy layer, the Realm is the identity-provider backing store. Adding tenant config (theme, SMTP, etc.) happens on the Space; adding a user happens *in* the Space's Realm.

### User

A natural person authenticated by Keycloak. Identified by id, with email, given/family name, MFA list, attributes, enabled flag. Belongs to one Organization (`memberOf: OrganizationId`) and carries assigned Roles. Distinct from a [C2 Agent](../../fixers/fixers-c2/CONTEXT.md#agent) — a User may control one or more Agents on a chain, but the identities live in different systems.

### Organization

A legal / functional entity inside a Space. Identified, named, addressed; carries assigned Roles and attributes. Users are members of one Organization.

### Privilege hierarchy

Privilege is the sealed root of IM's authorization vocabulary, with three concrete shapes:

```
                                 ┌─────────────────┐
                                 │     Role        │  assignable to target kinds
                                 │  (RoleModel)    │  (ORGANIZATION | USER | API_KEY)
                                 │  + permissions  │  + bindings
                                 └────────┬────────┘
                                          │ contains
                                          ▼
                                 ┌─────────────────┐
                                 │   Permission    │  named bundle of features
                                 │ (PermissionMdl) │
                                 └────────┬────────┘
                                          │ contains
                                          ▼
                                 ┌─────────────────┐
                                 │     Feature     │  atomic capability
                                 │ (FeatureModel)  │  (e.g. "im_user_read")
                                 └─────────────────┘
```

- **Feature** — atomic capability. Identified, named, described. Cannot be subdivided. Concrete instances live in the `ImPermission` enum (e.g. `im_user_read`, `im_organization_write`, `im_apikey_*`, `im_space_*`, `im_role_*`, `im_mfa_force_otp`).
- **Permission** — a named bundle of Features. Apps reason in terms of Permissions when asking "is the caller allowed to do X?"
- **Role** — assignable Privilege. Carries a list of Permissions, a list of `bindings`, and a `targets: List<RoleTarget>` constraining which subject kinds it can be granted to.

### RoleTarget

`ORGANIZATION | USER | API_KEY`. Pins which kind of subject a Role can be granted to. Same Role definition can target multiple kinds.

### ApiKey

A machine subject equivalent to a User for authorization purposes; carries Roles (whose `targets` include `API_KEY`) and is authenticated by token instead of credentials.

### MFA

Multi-factor authentication binding on a User. List-valued — a User may have several MFA methods. `im_mfa_force_otp` is the canonical Feature that, when present on a User's effective Permission set, requires OTP.

### Client

A Keycloak client representation (`ClientModel`). Used when an external app needs to talk to a Space's Realm; not to be confused with [F2 Client](../../fixers/fixers-f2/CONTEXT.md#f2-client) (the Ktor HTTP client family).

### AuthedUser & AuthSubRealm

Cross-cutting auth context types in `im-commons`: `AuthedUser` is the resolved authenticated principal + their Space, `AuthSubRealm` is the realm abstraction used at the boundary.

## Module map

- `im-api/` — Spring Boot HTTP gateway + config.
- `im-core/` — domain models, Keycloak bridge, MFA, client.
- `im-commons/` — shared auth context, permissions, DTOs.
- `im-f2/` — F2-shaped APIs / Commands / Queries (user, organization, privilege, space, apikey).
- `im-keycloak/` — Keycloak plugin suite (event listener, DB migration, token generation, role mapper).
- `im-infra/` — Keycloak admin client wrapper, Redis cache.
- `im-bdd/` — Cucumber BDD tests.
- `im-script/` — CLI scripts (core, gateway, init, space config/creation).

## Published artifacts

Maven group `io.komune.im`. Notable: `im-commons-domain`, `im-user-{domain,api,client}`, `im-organization-{domain,api}`, `im-privilege-{domain,api}`, `im-space-{domain,api}`, `im-apikey-*`. Bundled under `connect-im` in the publish DSL.

## Cross-references

- F2 function shape + CQRS messages: [../../fixers/fixers-f2/CONTEXT.md](../../fixers/fixers-f2/CONTEXT.md).
- State machines (used internally for some entity lifecycles): [../../fixers/fixers-s2/CONTEXT.md](../../fixers/fixers-s2/CONTEXT.md).
- Off-chain identity vs on-chain identity: [../../fixers/fixers-c2/CONTEXT.md#agent](../../fixers/fixers-c2/CONTEXT.md#agent).
- File attachments for User / Organization profiles: [../connect-fs/CONTEXT.md](../connect-fs/CONTEXT.md).
- UI binding: [g2-im](../../fixers/fixers-g2/CONTEXT.md#ui-binding).
- Currently still pulls deprecated [fixers-d2](../../fixers/fixers-d2/CONTEXT.md) for documentation generation; migration TODO.
- Layer position (top of the DAG): [../../docs/adr/0001-submodule-dependency-layers.md](../../docs/adr/0001-submodule-dependency-layers.md).
