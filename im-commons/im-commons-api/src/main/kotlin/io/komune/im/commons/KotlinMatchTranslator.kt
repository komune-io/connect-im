package io.komune.im.commons

import f2.dsl.cqrs.filter.AndMatch
import f2.dsl.cqrs.filter.CollectionMatch
import f2.dsl.cqrs.filter.ComparableMatch
import f2.dsl.cqrs.filter.ComparableMatchCondition
import f2.dsl.cqrs.filter.ExactMatch
import f2.dsl.cqrs.filter.Match
import f2.dsl.cqrs.filter.OrMatch
import f2.dsl.cqrs.filter.StringMatch
import f2.dsl.cqrs.filter.StringMatchCondition
import kotlin.experimental.ExperimentalTypeInference

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
fun <E, F> Collection<E>.match(matcher: Match<F>?, getField: (E) -> F): Collection<E> {
    if (matcher == null) {
        return this
    }

    return filter { entity -> match(getField(entity), matcher) }
}

@JvmName("matchCollection")
fun <E, F> Collection<E>.match(matcher: Match<F>?, getField: (E) -> Collection<F>): Collection<E> {
    if (matcher == null) {
        return this
    }

    return filter { entity ->
        getField(entity).any { match(it, matcher) }
    }
}

private fun <F> match(field: F, matcher: Match<F>): Boolean {
    return when {
        matcher.negative -> !match(field, matcher.not())
        else -> when (matcher) {
            is CollectionMatch -> match(field, matcher)
            is ExactMatch -> match(field, matcher)
            is StringMatch -> match(field as String?, matcher)
            is ComparableMatch -> match(field, matcher)
            is AndMatch -> match(field, matcher)
            is OrMatch -> match(field, matcher)
        }
    }
}

private fun <F> match(field: F, matcher: AndMatch<F>): Boolean {
    return matcher.matches.all { match(field, it) }
}

private fun <F> match(field: F, matcher: OrMatch<F>): Boolean {
    return matcher.matches.any { match(field, it) }
}

private fun <F> match(field: F, matcher: CollectionMatch<F>): Boolean {
    return field in matcher.values
}

private fun <F> match(field: F, matcher: ExactMatch<F>): Boolean {
    return field == matcher.value
}

private fun match(field: String?, matcher: StringMatch): Boolean {
    val regexOptions = setOf(RegexOption.DOT_MATCHES_ALL)
    val regex = when (matcher.condition) {
        StringMatchCondition.EXACT -> Regex(matcher.value, regexOptions)
        StringMatchCondition.STARTS_WITH -> Regex("${matcher.value}(.*)", regexOptions)
        StringMatchCondition.ENDS_WITH -> Regex("(.*)${matcher.value}", regexOptions)
        StringMatchCondition.CONTAINS -> Regex("(.*)${matcher.value}(.*)", regexOptions)
    }
    return field != null && regex.matches(field)
}

private fun <F> match(field: F, matcher: ComparableMatch<F>): Boolean {
    if (field !is Comparable<*>) {
        throw IllegalArgumentException("Field with value [${field}] cannot be compared")
    }
    @Suppress("UNCHECKED_CAST")
    field as Comparable<F>

    return when (matcher.condition) {
        ComparableMatchCondition.EQ -> field == matcher.value
        ComparableMatchCondition.GT -> field > matcher.value
        ComparableMatchCondition.GTE -> field >= matcher.value
        ComparableMatchCondition.LT -> field < matcher.value
        ComparableMatchCondition.LTE -> field <= matcher.value
    }
}
