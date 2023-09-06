package city.smartb.im.bdd.exception

import city.smartb.im.bdd.ImCucumberStepsDefinition
import city.smartb.im.commons.ExceptionCodes
import city.smartb.im.core.privilege.api.exception.PrivilegeWrongTargetException
import f2.spring.exception.ConflictException
import f2.spring.exception.ForbiddenAccessException
import f2.spring.exception.MessageConverterException
import f2.spring.exception.NotFoundException
import io.cucumber.java8.En
import s2.bdd.assertion.AssertionBdd
import s2.bdd.assertion.exceptions
import s2.bdd.data.parser.safeExtract

class ExceptionAssertionSteps: En, ImCucumberStepsDefinition()  {
    init {
        DataTableType(::exceptionAssertionParams)

        Then("An exception should be thrown:") { params: ExceptionAssertionParams ->
            step {
                AssertionBdd.exceptions(context)
                    .assertThat(params.code.toExceptionClass())
                    .hasBeenThrown(params.times)
            }
        }

        Then("No exception should be thrown:") { params: ExceptionAssertionParams ->
            step {
                AssertionBdd.exceptions(context)
                    .assertThat(params.code.toExceptionClass())
                    .hasNotBeenThrown()
            }
        }
    }

    @Suppress("MagicNumber")
    private fun Int.toExceptionClass() = when (this) {
        400 -> MessageConverterException::class
        403 -> ForbiddenAccessException::class
        404 -> NotFoundException::class
        409 -> ConflictException::class
        ExceptionCodes.privilegeWrongTarget() -> PrivilegeWrongTargetException::class
        else -> throw IllegalArgumentException("Unknown exception code [$this]")
    }

    private fun exceptionAssertionParams(entry: Map<String, String>) = ExceptionAssertionParams(
        code = entry.safeExtract("code").toInt(),
        times = entry["times"]?.toInt() ?: 1
    )

    private data class ExceptionAssertionParams(
        val code: Int,
        val times: Int
    )
}
