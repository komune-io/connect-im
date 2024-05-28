package io.komune.im.commons.model

import f2.client.ktor.http.plugin.model.RealmId

/**
 * @d2 hidden
 * @visual json "c790642c-4ed2-4cfc-bc45-905a39006e99"
 */
typealias OrganizationId = String

/**
 * @d2 hidden
 * @visual json "ad4adcc1-2633-4f2b-8b66-aaca39f45146"
 */
typealias UserId = String

/**
 * @d2 hidden
 * @visual json "d54f1492-8624-4ad3-851d-1b19d03b0c24"
 */
typealias PrivilegeId = String

/**
 * @d2 hidden
 */
typealias PrivilegeIdentifier = String

/**
 * @d2 hidden
 * @visual json "868044f8-1852-43eb-897d-067bc4631396"
 */
typealias PermissionId = PrivilegeId

/**
 * @d2 hidden
 */
typealias PermissionIdentifier = PrivilegeIdentifier

/**
 * @d2 hidden
 * @visual json "a0c76771-c5f9-4bde-8c30-901a22f570ba"
 */
typealias RoleId = PrivilegeId

/**
 * @d2 hidden
 */
typealias RoleIdentifier = PrivilegeIdentifier

/**
 * @d2 hidden
 * @visual json "2b7ef785-a56e-4d4e-98bc-0414e3518049"
 */
typealias ClientId = String

/**
 * @d2 hidden
 */
typealias ClientIdentifier = String

/**
 * @d2 hidden
 * @visual json "85171569-8970-45fb-b52a-85b59f06c292"
 */
typealias SpaceIdentifier = RealmId
