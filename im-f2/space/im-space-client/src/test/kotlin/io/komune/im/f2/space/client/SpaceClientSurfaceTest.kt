package io.komune.im.f2.space.client

import kotlin.reflect.KClass
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 * Each [SpaceClient] method delegates with `this::method.name`, so the **method name is the
 * remote function name**. Renaming one silently repoints the call at a route the server
 * does not serve, with no compile error on either side.
 */
class SpaceClientSurfaceTest {

    private val operations: List<String> = SpaceClient::class.members.map { it.name }

    @Test
    fun exposesEveryDocumentedOperation() {
        EXPECTED.forEach { assertThat(operations).contains(it) }
    }

    @Test
    fun theClientClassIsOpenSoConsumersCanDecorateIt() {
        assertThat((SpaceClient::class as KClass<*>).isFinal).isFalse()
    }

    companion object {
        private val EXPECTED = listOf("spaceDefine", "spaceDelete", "spaceGet", "spacePage")
    }
}
