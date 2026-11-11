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

    private val me = user(id = "u1", memberOf = "o1")
    private val colleague = user(id = "u2", memberOf = "o1")
    private val stranger = user(id = "u3", memberOf = "o2")
    private val orphan = user(id = "u4", memberOf = null)

    private fun authed(id: String, memberOf: String?, permissions: List<ImPermission>) = AuthedUser(
        id = id,
        identifier = "$id@komune.io",
        memberOf = memberOf,
        roles = permissions.map(ImPermission::identifier).toTypedArray(),
        acr = null
    )

    private fun tokenOfMe(vararg permissions: ImPermission) =
        authed(id = "u1", memberOf = "o1", permissions = permissions.toList())

    private fun tokenOfOrphan(vararg permissions: ImPermission) =
        authed(id = "u4", memberOf = null, permissions = permissions.toList())

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

    @Test
    fun aUserReadsItselfOnlyWithTheSelfPermission() {
        assertTrue(UserPolicies.canGet(tokenOfMe(ImPermission.IM_MY_USER), me))
        assertFalse(UserPolicies.canGet(tokenOfMe(), me))
        assertFalse(UserPolicies.canGet(tokenOfMe(ImPermission.IM_MY_ORGANIZATION_READ), me))
    }

    @Test
    fun aUserReadsItsColleaguesOnlyWithTheOwnOrganizationPermission() {
        assertTrue(UserPolicies.canGet(tokenOfMe(ImPermission.IM_MY_ORGANIZATION_READ), colleague))
        assertFalse(UserPolicies.canGet(tokenOfMe(), colleague))
        assertFalse(UserPolicies.canGet(tokenOfMe(ImPermission.IM_MY_ORGANIZATION_READ), stranger))
    }

    @Test
    fun usersWithoutOrganizationAreNotColleagues() {
        val orphanToken = tokenOfOrphan(ImPermission.IM_MY_ORGANIZATION_READ)

        assertFalse(UserPolicies.canGet(orphanToken, user(id = "u5", memberOf = null)))
        assertFalse(UserPolicies.canGet(orphanToken, null))
        assertFalse(UserPolicies.canGet(tokenOfOrphan(), null))
    }

    @Test
    fun aUserReaderReadsAnyone() {
        val reader = authed(id = "admin", memberOf = null, permissions = listOf(ImPermission.IM_USER_READ))

        assertTrue(UserPolicies.canGet(reader, stranger))
        assertTrue(UserPolicies.canGet(reader, null))
    }

    @Test
    fun aUserUpdatesItselfOnlyWithTheSelfPermission() {
        assertTrue(UserPolicies.canUpdate(tokenOfMe(ImPermission.IM_MY_USER), me))
        assertFalse(UserPolicies.canUpdate(tokenOfMe(), me))
        assertFalse(UserPolicies.canUpdate(tokenOfMe(ImPermission.IM_MY_ORGANIZATION_READ), me))
    }

    @Test
    fun aUserWithoutOrganizationUpdatesItselfOnlyWithTheSelfPermission() {
        assertTrue(UserPolicies.canUpdate(tokenOfOrphan(ImPermission.IM_MY_USER), orphan))
        assertFalse(UserPolicies.canUpdate(tokenOfOrphan(), orphan))
    }

    @Test
    fun theSelfPermissionDoesNotReachOthers() {
        val token = tokenOfMe(ImPermission.IM_MY_USER, ImPermission.IM_MY_ORGANIZATION_READ)

        assertFalse(UserPolicies.canUpdate(token, colleague))
        assertFalse(UserPolicies.canUpdate(tokenOfOrphan(ImPermission.IM_MY_USER), user(id = "u5", memberOf = null)))
    }

    @Test
    fun writersKeepTheirAccess() {
        val orgWriter = authed(id = "w", memberOf = "o1", permissions = listOf(ImPermission.IM_USER_WRITE))
        val admin = authed(
            id = "admin",
            memberOf = null,
            permissions = listOf(ImPermission.IM_USER_WRITE, ImPermission.IM_ORGANIZATION_WRITE),
        )

        assertTrue(UserPolicies.canUpdate(orgWriter, colleague))
        assertFalse(UserPolicies.canUpdate(orgWriter, stranger))
        assertTrue(UserPolicies.canUpdate(admin, stranger))
        assertTrue(UserPolicies.canDisable(admin, stranger))
        assertTrue(UserPolicies.canEnable(admin, stranger))
        assertTrue(UserPolicies.canDelete(admin, stranger))
    }

    @Test
    fun nobodyDisablesEnablesOrDeletesItself() {
        val token = tokenOfMe(ImPermission.IM_MY_USER, ImPermission.IM_USER_WRITE)

        assertFalse(UserPolicies.canDisable(token, me))
        assertFalse(UserPolicies.canEnable(token, me))
        assertFalse(UserPolicies.canDelete(token, me))
    }

    @Test
    fun mfaIsConfiguredOnOneselfOnlyWithTheSelfPermission() {
        assertTrue(UserMfaPolicies.canConfigureMfa(tokenOfMe(ImPermission.IM_MY_USER), me))
        assertFalse(UserMfaPolicies.canConfigureMfa(tokenOfMe(), me))
        assertFalse(UserMfaPolicies.canConfigureMfa(tokenOfMe(ImPermission.IM_MY_USER), colleague))
    }

    @Test
    fun mfaIsDisabledOnOneselfOnlyWithTheSelfPermissionAndWhenNotForced() {
        assertTrue(UserMfaPolicies.canDisableMfa(tokenOfMe(ImPermission.IM_MY_USER), me))
        assertFalse(UserMfaPolicies.canDisableMfa(tokenOfMe(), me))
        assertFalse(
            UserMfaPolicies.canDisableMfa(
                tokenOfMe(ImPermission.IM_MY_USER, ImPermission.IM_FORCE_MFA_OTP),
                me,
            )
        )
        assertFalse(UserMfaPolicies.canDisableMfa(tokenOfMe(ImPermission.IM_MY_USER), colleague))
    }

    @Test
    fun disablingMfaStillNeedsAnOtpLogin() {
        val token = tokenOfMe(ImPermission.IM_MY_USER)

        assertFalse(UserMfaPolicies.canDisableMfaAcr(token))
        assertTrue(UserMfaPolicies.canDisableMfaAcr(token.copy(acr = ImMfaPasswordOtpFlowAcr.PASSWORD_OTP.key)))
    }

    @Test
    fun userRefsNeedAReadPermission() {
        assertTrue(UserPolicies.checkRefList(tokenOfMe(ImPermission.IM_USER_READ)))
        assertTrue(UserPolicies.checkRefList(tokenOfMe(ImPermission.IM_MY_ORGANIZATION_READ)))
        assertFalse(UserPolicies.checkRefList(tokenOfMe()))
    }
}
