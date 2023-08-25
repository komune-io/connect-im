package city.smartb.im.commons

class SimpleCache<K: Any, V>(
    private val fetch: suspend (key: K) -> V
) {
    private val cache = mutableMapOf<K, V>()

    suspend fun get(key: K): V {
        return cache.getOrPut(key) { fetch(key) }
    }

    fun register(key: K, value: V) {
        cache[key] = value
    }
}
