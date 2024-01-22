package mb.parser

actual fun <R> transformLines(
    year: AdventYear,
    day: AdventDay,
    part: AdventPart,
    case: AdventCase,
    order: AdventOrder,
    transform: (index: Int, String) -> R
): List<R> {
    TODO("Not yet implemented")
}

actual fun makeGroup(
    year: AdventYear,
    day: AdventDay,
    part: AdventPart,
    case: AdventCase,
    order: AdventOrder,
    vararg counts: Int
): List<List<String>> {
    TODO("Not yet implemented")
}

actual fun <R> parts(
    year: AdventYear,
    day: AdventDay,
    part: AdventPart,
    case: AdventCase,
    order: AdventOrder,
    needTrim: Boolean,
    transform: (List<String>) -> R
): List<R> {
    TODO("Not yet implemented")
}