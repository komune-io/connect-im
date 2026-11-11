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

    return filter { entity -> matches(field = getField(entity), matcher = matcher) }
}

@JvmName("matchCollection")
fun <E, F> Collection<E>.match(matcher: Match<F>?, getField: (E) -> Collection<F>): Collection<E> {
    if (matcher == null) {
        return this
    }

    return filter { entity ->
        getField(entity).any { field -> matches(field = field, matcher = matcher) }
    }
}

private fun <F> matches(field: F, matcher: Match<F>): Boolean = if (matcher.isNegative) {
    !matches(field = field, matcher = matcher.not())
} else {
    when (matcher) {
        is CollectionMatch -> matchesCollection(field = field, matcher = matcher)
        is ExactMatch -> matchesExact(field = field, matcher = matcher)
        is StringMatch -> matchesString(field = field?.let { value -> value as String }, matcher = matcher)
        is ComparableMatch -> matchesComparable(field = field, matcher = matcher)
        is AndMatch -> matchesAnd(field = field, matcher = matcher)
        is OrMatch -> matchesOr(field = field, matcher = matcher)
    }
}

private fun <F> matchesAnd(field: F, matcher: AndMatch<F>): Boolean =
    matcher.matches.all { subMatcher -> matches(field = field, matcher = subMatcher) }

private fun <F> matchesOr(field: F, matcher: OrMatch<F>): Boolean =
    matcher.matches.any { subMatcher -> matches(field = field, matcher = subMatcher) }

private fun <F> matchesCollection(field: F, matcher: CollectionMatch<F>): Boolean = field in matcher.values

private fun <F> matchesExact(field: F, matcher: ExactMatch<F>): Boolean = field == matcher.value

private fun matchesString(field: String?, matcher: StringMatch): Boolean {
    val regexOptions = setOf(RegexOption.DOT_MATCHES_ALL)
    val regex = when (matcher.condition) {
        StringMatchCondition.EXACT -> Regex(matcher.value, regexOptions)
        StringMatchCondition.STARTS_WITH -> Regex("${matcher.value}(.*)", regexOptions)
        StringMatchCondition.ENDS_WITH -> Regex("(.*)${matcher.value}", regexOptions)
        StringMatchCondition.CONTAINS -> Regex("(.*)${matcher.value}(.*)", regexOptions)
    }
    return field != null && regex.matches(field)
}

private fun <F> matchesComparable(field: F, matcher: ComparableMatch<F>): Boolean {
    require(field is Comparable<*>) { "Field with value [${field?.toString() ?: "null"}] cannot be compared" }
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
