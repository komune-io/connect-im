package city.smartb.im.bdd.data

import kotlin.reflect.KClass

class ExceptionList {
    private val exceptions = mutableListOf<Exception>()

    val list: List<Exception>
        get() = exceptions.toList()

    fun add(e: Exception) {
        exceptions.add(e)
    }

    @Suppress("UNCHECKED_CAST")
    fun <E: Exception> lastOfType(klass: KClass<E>): E? {
        return exceptions.lastOrNull(klass::isInstance) as E?
    }

    fun reset() {
        exceptions.clear()
    }
}
