package io.komune.im.commons

import f2.dsl.cqrs.page.Page
import f2.dsl.cqrs.page.PageDTO
import io.komune.im.commons.utils.mapAsync

abstract class Transformer<ORIGIN, RESULT> {
    abstract suspend fun transform(item: ORIGIN): RESULT

    @JvmName("transformNullable")
    suspend fun transform(item: ORIGIN?): RESULT? {
        return item?.let { transform(it) }
    }

    suspend fun transform(items: Collection<ORIGIN>): List<RESULT> {
        return items.mapAsync(this::transform)
    }

    suspend fun transform(page: PageDTO<ORIGIN>): PageDTO<RESULT> {
        return transform(page) { items, total -> Page(items = items, total = total) }
    }

    suspend fun <R: PageDTO<RESULT>> transform(
        page: PageDTO<ORIGIN>,
        pageBuilder: (items: List<RESULT>, total: Int) -> R
    ): R {
        return pageBuilder(transform(page.items), page.total)
    }
}
