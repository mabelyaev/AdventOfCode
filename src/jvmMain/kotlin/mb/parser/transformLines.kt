package mb.parser

import java.io.InputStreamReader

actual fun <R> transformLines(
    year: AdventYear,
    day: AdventDay,
    part: AdventPart,
    case: AdventCase,
    order: AdventOrder,
    transform: (index: Int, String) -> R
): List<R> {
    return reader(year, day, part, case, order).readLines().mapIndexed(transform)
}

actual fun <R> parts(
    year: AdventYear,
    day: AdventDay,
    part: AdventPart,
    case: AdventCase,
    order: AdventOrder,
    needTrim: Boolean,
    transform: (List<String>) -> R): List<R> = buildList {
    var lines = reader(year, day, part, case, order).readLines()
    var groupOfStrings = ArrayList<String>()


    lines.forEach { s ->
        val line = (if (needTrim) s.trim() else s).takeIf { it.isNotBlank() }
        if (line != null) {
            groupOfStrings.add(line)
        } else {
            add(transform(groupOfStrings))
            groupOfStrings = ArrayList<String>()
        }
    }
    if (groupOfStrings.isNotEmpty()) {
        add(transform(groupOfStrings))
    }
}

actual fun makeGroup(
    year: AdventYear,
    day: AdventDay,
    part: AdventPart,
    case: AdventCase,
    order: AdventOrder,
    vararg counts: Int
): List<List<String>> {
    var lines = reader(year, day, part, case, order).readLines()

    val result = mutableListOf<List<String>>()
    counts.forEachIndexed { index, c ->
        result.add(lines.subList(0, c))
        lines = lines.drop(c)
    }
    if (lines.isNotEmpty()) {
        result.add(lines)
    }
    return result
}

private fun reader(
    year: AdventYear,
    day: AdventDay,
    part: AdventPart,
    case: AdventCase,
    order: AdventOrder,
): InputStreamReader {
    val orderStr = if (order == AdventOrder.Order1) "" else "_${order.number}"
    val name = "/${year.number}/${day.name}_${part.number}_${case.name.lowercase()}${orderStr}"

    return year::class.java.getResource(name)
        .openStream().reader()

}