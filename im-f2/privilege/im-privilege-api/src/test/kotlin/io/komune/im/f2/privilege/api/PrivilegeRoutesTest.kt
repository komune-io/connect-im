package io.komune.im.f2.privilege.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Privileges are split across three endpoints that the single `PrivilegeClient` calls,
 * so the route names have to line up across all four classes.
 */
class PrivilegeRoutesTest {

    private fun routesOf(clazz: Class<*>) = clazz.declaredMethods.map { it.name }

    @Test
    fun `permission routes are exposed`() {
        assertThat(routesOf(PermissionEndpoint::class.java))
            .contains("permissionGet", "permissionList", "permissionDefine")
    }

    @Test
    fun `role routes are exposed`() {
        assertThat(routesOf(RoleEndpoint::class.java))
            .contains("roleGet", "roleList", "roleDefine")
    }

    @Test
    fun `feature routes are exposed`() {
        assertThat(routesOf(FeatureEndpoint::class.java))
            .contains("featureGet", "featureList", "featureDefine")
    }

    @Test
    fun `the three endpoints do not overlap`() {
        val permission = routesOf(PermissionEndpoint::class.java).filter { it.startsWith("permission") }
        val role = routesOf(RoleEndpoint::class.java).filter { it.startsWith("role") }
        val feature = routesOf(FeatureEndpoint::class.java).filter { it.startsWith("feature") }

        assertThat(permission + role + feature).doesNotHaveDuplicates()
    }
}
