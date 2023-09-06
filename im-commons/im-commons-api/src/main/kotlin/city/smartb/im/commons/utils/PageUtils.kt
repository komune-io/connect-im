package city.smartb.im.commons.utils

fun <T> List<T>.page(offset: Int?, limit: Int?): Pair<List<T>, Int> {
    if (isEmpty() || offset == null || limit == null) {
        return this to size
    }
    val elementAtOrNull = { index: Int -> this.elementAtOrNull(index) != null }
    val fromIndex = offset.takeIf (elementAtOrNull) ?: 0
    val toIndex = (fromIndex + limit).takeIf (elementAtOrNull) ?: size
    return subList(fromIndex, toIndex) to size
}
