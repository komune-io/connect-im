package city.smartb.im.bdd.data.parser

import city.smartb.im.bdd.exception.NullDataTableParamException
import city.smartb.im.bdd.exception.IllegalDataTableParamException

fun <R: Any> Map<String, String>.extract(
    key: String, parseErrorMessage: String, parser: (String) -> R?
) = get(key)?.let {
    parser(it) ?: throw IllegalDataTableParamException(key, parseErrorMessage)
}

fun Map<String, String>.safeExtract(key: String) = get(key) ?: throw NullDataTableParamException(key)

fun <R: Any> Map<String, String>.safeExtract(
    key: String, parseErrorMessage: String, parser: (String) -> R?
) = extract(key, parseErrorMessage, parser) ?: throw NullDataTableParamException(key)

fun Map<String, String>.extractList(key: String) = get(key)?.split(",")?.map(String::trim)

fun <R: Any> Map<String, String>.extractList(
    key: String, parseErrorMessage: String, parser: (String) -> R?
) = extractList(key)?.map {
    parser(it) ?: throw IllegalDataTableParamException(key, parseErrorMessage)
}

fun Map<String, String>.safeExtractList(key: String) = extractList(key) ?: throw NullDataTableParamException(key)

fun <R: Any> Map<String, String>.safeExtractList(
    key: String, parseErrorMessage: String, parser: (String) -> R?
) = extractList(key, parseErrorMessage, parser) ?: throw NullDataTableParamException(key)
