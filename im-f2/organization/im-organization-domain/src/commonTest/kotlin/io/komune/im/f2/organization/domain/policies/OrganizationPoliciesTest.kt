package io.komune.im.f2.organization.domain.policies

import io.komune.im.commons.auth.AuthedUser
import io.komune.im.commons.auth.ImPermission
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Reading one's own organization is a permission like any other, so that a token without
 * the IM features (a third-party application's) cannot read it.
 */
class OrganizationPoliciesTest {

    private fun authed(memberOf: String?, vararg permissions: ImPermission) = AuthedUser(
        id = "u1",
        identifier = "u1@komune.io",
        memberOf = memberOf,
        roles = permissions.map(ImPermission::identifier).toTypedArray(),
        acr = null
    )

    @Test
    fun aMemberReadsItsOrganizationOnlyWithTheOwnOrganizationPermission() {
        assertTrue(OrganizationPolicies.canGet(authed("o1", ImPermission.IM_MY_ORGANIZATION_READ), "o1"))
        assertFalse(OrganizationPolicies.canGet(authed("o1"), "o1"))
    }

    @Test
    fun theOwnOrganizationPermissionDoesNotReachOtherOrganizations() {
        assertFalse(OrganizationPolicies.canGet(authed("o1", ImPermission.IM_MY_ORGANIZATION_READ), "o2"))
    }

    @Test
    fun anOrganizationReaderReadsAnyOrganization() {
        assertTrue(OrganizationPolicies.canGet(authed(null, ImPermission.IM_ORGANIZATION_READ), "o2"))
    }

    @Test
    fun organizationRefsNeedAReadPermission() {
        assertTrue(OrganizationPolicies.checkRefList(authed(null, ImPermission.IM_ORGANIZATION_READ)))
        assertTrue(OrganizationPolicies.checkRefList(authed("o1", ImPermission.IM_MY_ORGANIZATION_READ)))
        assertFalse(OrganizationPolicies.checkRefList(authed("o1")))
        assertFalse(OrganizationPolicies.checkRefList(null))
    }
}
