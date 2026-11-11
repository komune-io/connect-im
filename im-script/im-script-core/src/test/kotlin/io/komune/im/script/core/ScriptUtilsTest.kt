package io.komune.im.script.core

import io.komune.im.commons.utils.ConfigurationException
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

class ScriptUtilsTest {

    private val logger = LoggerFactory.getLogger(ScriptUtilsTest::class.java)

    @Test
    fun `succeeds on the first attempt without retrying`() = runTest {
        var attempts = 0

        val isSuccess = retryOnThrow(actionName = "action", logger = logger) { attempts++ }

        assertThat(isSuccess).isTrue()
        assertThat(attempts).isEqualTo(1)
    }

    @Test
    fun `retries until the action stops throwing`() = runTest {
        var attempts = 0

        val isSuccess = retryOnThrow(actionName = "action", retryDelayMillis = 0, logger = logger) {
            attempts++
            if (attempts < 3) error("not yet")
        }

        assertThat(isSuccess).isTrue()
        assertThat(attempts).isEqualTo(3)
    }

    @Test
    fun `gives up after maxRetries and reports failure rather than throwing`() = runTest {
        var attempts = 0

        val isSuccess = retryOnThrow(actionName = "action", maxRetries = 2, retryDelayMillis = 0, logger = logger) {
            attempts++
            error("always fails")
        }

        assertThat(isSuccess).isFalse()
        assertThat(attempts).isEqualTo(2)
    }

    @Test
    fun `the default retry budget is five attempts`() = runTest {
        var attempts = 0

        val isSuccess = retryOnThrow(actionName = "action", retryDelayMillis = 0, logger = logger) {
            attempts++
            error("always fails")
        }

        assertThat(isSuccess).isFalse()
        assertThat(attempts).isEqualTo(5)
    }

    @Test
    fun `a configuration error is thrown at once, without a retry`() = runTest {
        var attempts = 0

        val thrown = try {
            val isSuccess = retryOnThrow(actionName = "action", retryDelayMillis = 0, logger = logger) {
                attempts++
                throw ConfigurationException("missing")
            }
            error("returned $isSuccess")
        } catch (e: CancellationException) {
            throw e
        } catch (e: ConfigurationException) {
            e
        }

        assertThat(thrown).hasMessage("missing")
        assertThat(attempts).isEqualTo(1)
    }
}
