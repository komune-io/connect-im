package city.smartb.im.bdd

import city.smartb.im.bdd.data.TestContext
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID

open class CucumberStepsDefinition {

    @Autowired
    protected lateinit var context: TestContext

    protected fun String?.orRandom() = this ?: UUID.randomUUID().toString()

//    protected fun step(block: suspend () -> Unit) {
//        step({ e -> e !is AutomateException }, block)
//    }

    protected fun step(propagateException: (Exception) -> Boolean = { true }, block: suspend () -> Unit) {
        runBlocking {
            try {
                block()
            } catch (e: Exception) {
                e.printStackTrace()
                context.errors.add(e)
                if (propagateException(e)) {
                    throw e
                }
            }
        }
    }
}
