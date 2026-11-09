package io.komune.im.f2.user.domain.policies

import io.komune.im.commons.auth.AuthedUser
import io.komune.im.commons.auth.ImPermission
import io.komune.im.core.mfa.domain.model.ImMfaPasswordOtpFlowAcr
import io.komune.im.f2.organization.domain.model.OrganizationRef
import io.komune.im.f2.user.domain.model.User
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * A token handed to a third-party application carries the user's identity but none of the
 * IM permissions guarded by a feature. Acting on oneself or on one's organization must
 * therefore require a permission too, or such a token could change its user's password.
 */
internal class UserPoliciesTest {

    private fun authed(id: String, memberOf: String?, vararg permissions: ImPermission) = AuthedUser(
        id = id,
        identifier = "$id@komune.io",
        memberOf = memberOf,
        roles = permissions.map(ImPermission::identifier).toTypedArray(),
        acr = null
    )

    private fun user(id: String, memberOf: String?) = User(
        id = id,
        memberOf = memberOf?.let { OrganizationRef(id = it, name = it, roles = emptyList()) },
        email = "$id@komune.io",
        givenName = "John",
        familyName = "Deuf",
        address = null,
        phone = null,
        roles = emptyList(),
        attributes = emptyMap(),
        enabled = true,
        disabledBy = null,
        mfa = null,
        creationDate = 0,
        disabledDate = null
    )

    private val me = user("u1", "o1")
    private val colleague = user("u2", "o1")
    private val stranger = user("u3", "o2")
    private val orphan = user("u4", null)

    @Test
    fun aUserReadsItselfOnlyWithTheSelfPermission() {
        assertTrue(UserPolicies.canGet(authed("u1", "o1", ImPermission.IM_MY_USER), me))
        assertFalse(UserPolicies.canGet(authed("u1", "o1"), me))
        assertFalse(UserPolicies.canGet(authed("u1", "o1", ImPermission.IM_MY_ORGANIZATION_READ), me))
    }

    @Test
    fun aUserReadsItsColleaguesOnlyWithTheOwnOrganizationPermission() {
        assertTrue(UserPolicies.canGet(authed("u1", "o1", ImPermission.IM_MY_ORGANIZATION_READ), colleague))
        assertFalse(UserPolicies.canGet(authed("u1", "o1"), colleague))
        assertFalse(UserPolicies.canGet(authed("u1", "o1", ImPermission.IM_MY_ORGANIZATION_READ), stranger))
    }

    @Test
    fun usersWithoutOrganizationAreNotColleagues() {
        val orphanToken = authed("u4", null, ImPermission.IM_MY_ORGANIZATION_READ)

        assertFalse(UserPolicies.canGet(orphanToken, user("u5", null)))
        assertFalse(UserPolicies.canGet(orphanToken, null))
        assertFalse(UserPolicies.canGet(authed("u4", null), null))
    }

    @Test
    fun aUserReaderReadsAnyone() {
        val reader = authed("admin", null, ImPermission.IM_USER_READ)

        assertTrue(UserPolicies.canGet(reader, stranger))
        assertTrue(UserPolicies.canGet(reader, null))
    }

    @Test
    fun aUserUpdatesItselfOnlyWithTheSelfPermission() {
        assertTrue(UserPolicies.canUpdate(authed("u1", "o1", ImPermission.IM_MY_USER), me))
        assertFalse(UserPolicies.canUpdate(authed("u1", "o1"), me))
        assertFalse(UserPolicies.canUpdate(authed("u1", "o1", ImPermission.IM_MY_ORGANIZATION_READ), me))
    }

    @Test
    fun aUserWithoutOrganizationUpdatesItselfOnlyWithTheSelfPermission() {
        assertTrue(UserPolicies.canUpdate(authed("u4", null, ImPermission.IM_MY_USER), orphan))
        assertFalse(UserPolicies.canUpdate(authed("u4", null), orphan))
    }

    @Test
    fun theSelfPermissionDoesNotReachOthers() {
        val token = authed("u1", "o1", ImPermission.IM_MY_USER, ImPermission.IM_MY_ORGANIZATION_READ)

        assertFalse(UserPolicies.canUpdate(token, colleague))
        assertFalse(UserPolicies.canUpdate(authed("u4", null, ImPermission.IM_MY_USER), user("u5", null)))
    }

    @Test
    fun writersKeepTheirAccess() {
        val orgWriter = authed("w", "o1", ImPermission.IM_USER_WRITE)
        val admin = authed("admin", null, ImPermission.IM_USER_WRITE, ImPermission.IM_ORGANIZATION_WRITE)

        assertTrue(UserPolicies.canUpdate(orgWriter, colleague))
        assertFalse(UserPolicies.canUpdate(orgWriter, stranger))
        assertTrue(UserPolicies.canUpdate(admin, stranger))
        assertTrue(UserPolicies.canDisable(admin, stranger))
        assertTrue(UserPolicies.canEnable(admin, stranger))
        assertTrue(UserPolicies.canDelete(admin, stranger))
    }

    @Test
    fun nobodyDisablesEnablesOrDeletesItself() {
        val token = authed("u1", "o1", ImPermission.IM_MY_USER, ImPermission.IM_USER_WRITE)

        assertFalse(UserPolicies.canDisable(token, me))
        assertFalse(UserPolicies.canEnable(token, me))
        assertFalse(UserPolicies.canDelete(token, me))
    }

    @Test
    fun mfaIsConfiguredOnOneselfOnlyWithTheSelfPermission() {
        assertTrue(UserPolicies.canConfigureMfa(authed("u1", "o1", ImPermission.IM_MY_USER), me))
        assertFalse(UserPolicies.canConfigureMfa(authed("u1", "o1"), me))
        assertFalse(UserPolicies.canConfigureMfa(authed("u1", "o1", ImPermission.IM_MY_USER), colleague))
    }

    @Test
    fun mfaIsDisabledOnOneselfOnlyWithTheSelfPermissionAndWhenNotForced() {
        assertTrue(UserPolicies.canDisableMfa(authed("u1", "o1", ImPermission.IM_MY_USER), me))
        assertFalse(UserPolicies.canDisableMfa(authed("u1", "o1"), me))
        assertFalse(
            UserPolicies.canDisableMfa(authed("u1", "o1", ImPermission.IM_MY_USER, ImPermission.IM_FORCE_MFA_OTP), me)
        )
        assertFalse(UserPolicies.canDisableMfa(authed("u1", "o1", ImPermission.IM_MY_USER), colleague))
    }

    @Test
    fun disablingMfaStillNeedsAnOtpLogin() {
        val token = authed("u1", "o1", ImPermission.IM_MY_USER)

        assertFalse(UserPolicies.canDisableMfaAcr(token))
        assertTrue(UserPolicies.canDisableMfaAcr(token.copy(acr = ImMfaPasswordOtpFlowAcr.PASSWORD_OTP.key)))
    }

    @Test
    fun userRefsNeedAReadPermission() {
        assertTrue(UserPolicies.checkRefList(authed("u1", "o1", ImPermission.IM_USER_READ)))
        assertTrue(UserPolicies.checkRefList(authed("u1", "o1", ImPermission.IM_MY_ORGANIZATION_READ)))
        assertFalse(UserPolicies.checkRefList(authed("u1", "o1")))
    }
}
