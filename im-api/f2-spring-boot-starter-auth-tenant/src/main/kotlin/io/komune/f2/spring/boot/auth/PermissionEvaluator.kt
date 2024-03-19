package io.komune.f2.spring.boot.auth

import io.komune.f2.spring.boot.auth.AuthenticationProvider.hasRole
import org.springframework.stereotype.Service

const val ROLE_PREFIX = "ROLE_"
const val SUPER_ADMIN_ROLE = "super_admin"

@Service
class PermissionEvaluator{
    suspend fun isSuperAdmin(): Boolean {
        return hasRole(ROLE_PREFIX + SUPER_ADMIN_ROLE)
    }

    suspend fun checkOrganizationId(organizationId: String?): Boolean {
        if (organizationId == null) return false
        return AuthenticationProvider.getOrganizationId() == organizationId
    }
}
