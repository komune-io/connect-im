package city.smartb.im.bdd.data.parser

class EntryParser<R: Any>(
    private val parseErrorMessage: String,
    private val parser: (String) -> R?
) {
    fun single(entry: Map<String, String>, key: String): R? = entry.extract(key, parseErrorMessage, parser)
    fun safeSingle(entry: Map<String, String>, key: String): R = entry.safeExtract(key, parseErrorMessage, parser)
    fun list(entry: Map<String, String>, key: String): List<R>? = entry.extractList(key, parseErrorMessage, parser)
    fun safeList(entry: Map<String, String>, key: String): List<R> = entry.safeExtractList(key, parseErrorMessage, parser)
}
