package io.komune.im.f2.organization.client

import kotlin.reflect.KClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Each [OrganizationClient] method delegates with `this::method.name`, so the **method name is the
 * remote function name**. Renaming one silently repoints the call at a route the server
 * does not serve, with no compile error on either side.
 */
class OrganizationClientSurfaceTest {

    private val operations: List<String> = OrganizationClient::class.members.map { it.name }

    @Test
    fun exposesEveryDocumentedOperation() {
        EXPECTED.forEach { assertThat(operations).contains(it) }
    }

    @Test
    fun theClientClassIsOpenSoConsumersCanDecorateIt() {
        assertThat((OrganizationClient::class as KClass<*>).isFinal).isFalse()
    }

    companion object {
        private val EXPECTED = listOf(
            "organizationGet",
            "organizationRefGet",
            "organizationGetFromInsee",
            "organizationPage",
            "organizationRefList",
            "organizationCreate",
            "organizationUpdate",
            "organizationDisable",
            "organizationDelete"
        )
    }
}
