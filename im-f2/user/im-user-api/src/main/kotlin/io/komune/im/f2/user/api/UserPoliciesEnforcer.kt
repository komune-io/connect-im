package io.komune.im.f2.user.api

import f2.spring.exception.ForbiddenAccessException
import io.komune.im.commons.auth.policies.PolicyEnforcer
import io.komune.im.commons.model.OrganizationId
import io.komune.im.commons.model.UserId
import io.komune.im.f2.organization.domain.policies.OrganizationPolicies
import io.komune.im.f2.user.domain.command.UserUpdateCommand
import io.komune.im.f2.user.domain.model.UserDTO
import io.komune.im.f2.user.domain.policies.UserPolicies
import io.komune.im.f2.user.domain.query.UserPageQuery
import io.komune.im.f2.user.lib.UserFinderService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserPoliciesEnforcer(
    private val userFinderService: UserFinderService
): PolicyEnforcer() {

    private val logger = LoggerFactory.getLogger(UserPoliciesEnforcer::class.java)

    suspend fun checkGet(user: UserDTO? = null) = checkAuthed("get user") { authedUser ->
        UserPolicies.canGet(authedUser, user)
    }

    suspend fun checkPage() = checkAuthed("page users") { authedUser ->
        UserPolicies.canPage(authedUser)
    }

    suspend fun checkRefList() = checkAuthed("list user refs") { authedUser ->
        UserPolicies.checkRefList(authedUser)
    }

    suspend fun checkCreate(organizationId: OrganizationId?) = checkAuthed("create an user") { authedUser ->
        UserPolicies.canCreate(authedUser, organizationId)
    }

    suspend fun enforceUpdate(cmd: UserUpdateCommand) = enforceAuthed { authedUser ->
        checkUpdate(cmd.id)
        enforceMemberOf(cmd)
        enforceRoles(cmd)
    }
    suspend fun checkConfigureMfa(userId: UserId) = checkAuthed("configure mfa") { authedUser ->
        val user = userFinderService.get(userId)
        UserPolicies.canConfigureMfa(authedUser, user)
    }

    suspend fun checkDisableMfa(userId: UserId) = checkAuthed("disable mfa") { authedUser ->
        val user = userFinderService.get(userId)
        UserPolicies.canDisableMfa(authedUser, user)
    }

    suspend fun checkUpdate(userId: UserId) = checkAuthed("update an user") { authedUser ->
        val user = userFinderService.get(userId)
        UserPolicies.canUpdate(authedUser, user)
    }
    suspend fun enforceMemberOf(
        cmd: UserUpdateCommand
    ) = enforceAuthed { authedUser ->
        val user = userFinderService.get(cmd.id)
        logger.debug("Checking if memberOf can be updated for user with roles[${user.roles}] " +
            "and memberOf[${user.memberOf?.id}].")

        if(UserPolicies.canUpdateMemberOf(authedUser) || user.memberOf?.id == cmd.memberOf) {
            cmd
        } else {
            logger.warn("memberOf can't be updated, " +
                "memberOf will be set to the current user's organization[${user.memberOf?.id}].")
            cmd.copy(
                memberOf = user.memberOf?.id
            )
        }
    }
    suspend fun enforceRoles(
        cmd: UserUpdateCommand
    ) = enforceAuthed { authedUser ->
        val user = userFinderService.get(cmd.id)
        logger.debug("Checking if roles can be updated for user with roles[${user.roles}] " +
            "and memberOf[${user.memberOf?.id}].")
        val currentRoleIdentifier = user.roles.map { it.identifier }
        if(UserPolicies.canUpdateRole(authedUser) || currentRoleIdentifier == cmd.roles) {
            cmd
        } else {
            logger.warn("roles can't be updated, " +
                "roles will be set to the current user's organization[${user.roles}].")
            cmd.copy(
                roles = user.roles.map { it.identifier }
            )
        }
    }

    suspend fun checkDisable(userId: UserId) = checkAuthed("disable an user") { authedUser ->
        val user = userFinderService.get(userId)
        UserPolicies.canDisable(authedUser, user)
    }

    suspend fun checkDelete(userId: UserId) = checkAuthed("delete an user") { authedUser ->
        val user = userFinderService.get(userId)
        UserPolicies.canDelete(authedUser, user)
    }

    suspend fun enforcePageQuery(query: UserPageQuery): UserPageQuery = enforceAuthed { authedUser ->
        if (OrganizationPolicies.canList(authedUser)) {
            query
        } else if (query.organizationId != null && !OrganizationPolicies.canGet(authedUser, query.organizationId!!)) {
           throw ForbiddenAccessException("User can't list users from other organizations.")
        } else {
            query.copy(
                organizationId = authedUser.memberOf
            )
        }
    }
}
