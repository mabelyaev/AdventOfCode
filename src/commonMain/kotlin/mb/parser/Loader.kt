package mb.parser


fun <R> AdventDay.from2020By(
    case: AdventCase,
    part: AdventPart,
    order: AdventOrder,
    transform: (index: Int, String) -> R
): List<R> {
    return transformLines(AdventYear.Year2020, this, part, case, order, transform)
}

fun <R> AdventDay.from2021By(
    case: AdventCase,
    part: AdventPart,
    order: AdventOrder,
    transform: (index: Int, String) -> R
): List<R> {
    return transformLines(AdventYear.Year2021, this, part, case, order, transform)
}

fun <R> AdventDay.from2022By(
    case: AdventCase,
    part: AdventPart,
    order: AdventOrder,
    transform: (index: Int, String) -> R
): List<R> {
    return transformLines(AdventYear.Year2022, this, part, case, order, transform)
}

fun <R> AdventDay.parts2022By(
    case: AdventCase,
    part: AdventPart,
    order: AdventOrder,
    transform: (List<String>) -> R
): List<R> {
    return parts(AdventYear.Year2022, this, part, case, order, transform)
}


fun <R> AdventDay.from2023By(
    case: AdventCase,
    part: AdventPart,
    order: AdventOrder,
    transform: (index: Int, String) -> R
): List<R> {
    return transformLines(AdventYear.Year2023, this, part, case, order, transform)
}

fun AdventDay.groupFrom2023By(
    case: AdventCase,
    part: AdventPart,
    order: AdventOrder,
    vararg counts: Int
): List<List<String>> {
    return makeGroup(AdventYear.Year2023, this, part, case, order, *counts)
}

expect fun <R> transformLines(
    year: AdventYear,
    day: AdventDay,
    part: AdventPart,
    case: AdventCase,
    order: AdventOrder,
    transform: (index: Int, String) -> R
): List<R>

expect fun <R> parts(
    year: AdventYear,
    day: AdventDay,
    part: AdventPart,
    case: AdventCase,
    order: AdventOrder,
    transform: (List<String>) -> R): List<R>


expect fun  makeGroup(
    year: AdventYear,
    day: AdventDay,
    part: AdventPart,
    case: AdventCase,
    order: AdventOrder,
    vararg counts: Int
): List<List<String>>