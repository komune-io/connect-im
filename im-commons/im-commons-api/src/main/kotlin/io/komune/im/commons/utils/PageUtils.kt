package io.komune.im.commons.utils

import f2.dsl.cqrs.page.OffsetPagination
import f2.dsl.cqrs.page.Page
import f2.dsl.cqrs.page.PageDTO
import kotlin.math.min

fun <T> List<T>.page(offset: OffsetPagination?): PageDTO<T> {
    return page(offset?.offset, offset?.limit)
}

fun <T> List<T>.page(offset: Int?, limit: Int?): PageDTO<T> {
    val start = offset ?: 0
    val end = limit?.let { start + it } ?: size
    return Page(
        items = subList(
            min(start, size),
            min(end, size)
        ),
        total = size
    )
}
