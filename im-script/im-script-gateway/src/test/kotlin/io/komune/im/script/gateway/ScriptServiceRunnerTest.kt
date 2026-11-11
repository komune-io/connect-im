package io.komune.im.script.gateway

import io.komune.im.commons.utils.ConfigurationException
import io.komune.im.script.core.config.properties.ImRetryProperties
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.context.support.GenericApplicationContext

class ScriptServiceRunnerTest {

    private val logger = LoggerFactory.getLogger(ScriptServiceRunnerTest::class.java)
    private val retry = ImRetryProperties(max = 3, delayMillis = 0)

    @Test
    fun `a script that keeps failing stops the run with an error`() = runTest {
        var later = 0

        val thrown = try {
            runScripts(
                scripts = listOf("failing" to { error("down") }, "later" to { later++ }),
                retry = retry,
                logger = logger,
            )
            null
        } catch (e: CancellationException) {
            throw e
        } catch (e: IllegalStateException) {
            e
        }

        assertThat(thrown).hasMessage("Failed to run script failing")
        assertThat(later).isZero()
    }

    @Test
    fun `a configuration error is not retried and leaves the run`() = runTest {
        var attempts = 0

        val thrown = try {
            runScripts(
                scripts = listOf("config" to { attempts++; throw ConfigurationException("missing") }),
                retry = retry,
                logger = logger,
            )
            null
        } catch (e: CancellationException) {
            throw e
        } catch (e: ConfigurationException) {
            e
        }

        assertThat(thrown).hasMessage("missing")
        assertThat(attempts).isEqualTo(1)
    }

    @Test
    fun `a failing script leaves the runner with its error and the context closed`() {
        GenericApplicationContext().use { context ->
            context.refresh()

            assertThatThrownBy {
                runThenClose(
                    context = context,
                    scripts = listOf("failing" to { error("down") }),
                    retry = retry,
                    logger = logger,
                )
            }.hasMessage("Failed to run script failing")
            assertThat(context.isActive).isFalse()
        }
    }

    @Test
    fun `a successful run closes the context too`() {
        GenericApplicationContext().use { context ->
            context.refresh()
            var runs = 0

            runThenClose(context = context, scripts = listOf("ok" to { runs++ }), retry = retry, logger = logger)

            assertThat(runs).isEqualTo(1)
            assertThat(context.isActive).isFalse()
        }
    }
}
