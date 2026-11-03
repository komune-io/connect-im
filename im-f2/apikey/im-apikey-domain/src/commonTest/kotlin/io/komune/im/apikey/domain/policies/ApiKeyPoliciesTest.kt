package io.komune.im.apikey.domain.policies

import io.komune.im.commons.auth.AuthedUser
import io.komune.im.commons.auth.ImPermission
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Reading an API key on identity alone needs the own-organization permission too.
 */
internal class ApiKeyPoliciesTest {

    private fun authed(memberOf: String?, vararg permissions: ImPermission) = AuthedUser(
        id = "u1",
        identifier = "u1@komune.io",
        memberOf = memberOf,
        roles = permissions.map(ImPermission::identifier).toTypedArray(),
        acr = null
    )

    @Test
    fun anApiKeyReaderReadsAnyKey() {
        assertTrue(ApiKeyPolicies.canGet(authed(null, ImPermission.IM_APIKEY_READ), "k1"))
    }

    @Test
    fun theMemberBranchNeedsTheOwnOrganizationPermission() {
        assertTrue(ApiKeyPolicies.canGet(authed("k1", ImPermission.IM_MY_ORGANIZATION_READ), "k1"))
        assertFalse(ApiKeyPolicies.canGet(authed("k1"), "k1"))
        assertFalse(ApiKeyPolicies.canGet(authed("o1", ImPermission.IM_MY_ORGANIZATION_READ), "k1"))
    }
}
