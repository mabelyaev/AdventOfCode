package mb2021

import mb.parser.*
import mb2023.P2
import kotlin.math.max
import kotlin.math.min
import kotlin.test.Test
import kotlin.test.assertEquals

data class Line(val first: P2, val second: P2) {
    val isHorizontal: Boolean
        get() = first.y == second.y

    val isVertical: Boolean
        get() = first.x == second.x


    fun points(): List<P2> {
        return when {
            isHorizontal -> (min(first.x, second.x)..max(first.x, second.x)).map { P2(it, first.y) }
            isVertical -> (min(first.y, second.y)..max(first.y, second.y)).map { P2(first.x, it) }
            else -> {
                val minX = min(first.x, second.x)
                val maxX = max(first.x, second.x)
                val minY = min(first.y, second.y)
                val maxY = max(first.y, second.y)

                var x = first.x
                var y = first.y

                println("${first} vs ${second} ${first.x < second.x} ")


                val xChanger = if (first.x < second.x) {
                    {
                        x += 1
                    }
                } else {
                    {
                        x -= 1
                    }
                }
                val yChanger = if (first.y < second.y) { { y +=1 } } else { { y -= 1 } }

                buildList<P2> {
                    while (y <= maxY && x <= maxX && y >= minY && x >= minX) {
                        add(P2(x, y))
                        xChanger()
                        yChanger()
                    }
                }

            }
        }
    }
}

fun buildPointDiagram(lines: List<Line>): Array<IntArray> {
    val maxPosition =
        lines.map { line -> max(max(line.first.x, line.second.x), max(line.first.y, line.second.y)) }.max()


    val map = Array<IntArray>(maxPosition + 1) {
        IntArray(maxPosition + 1) {
            0
        }
    }
    lines.forEach {
        it.points().forEach {
            map[it.y][it.x] += 1
        }

    }
    return map
}

fun Array<IntArray>.countPountsWhereAtLeastWwoLinesOverlap(): Int {
    val countMoreOne = map {
        it.count { it > 1 }
    }.sum()
    return countMoreOne
}

fun Array<IntArray>.print() {
    forEach { row ->
        row.forEach { n ->
            print(if (n > 0) n.toString() else ".")
        }
        println()
    }
}

class Day05Of2021Tests {

    @Test
    fun part1Example() {
        val lines = readEntries(AdventCase.Example)

        assertEquals(10, lines.size)

        val filtered = lines.filter { it.isVertical || it.isHorizontal }

        assertEquals(6, filtered.size)

        val diagram = buildPointDiagram(filtered)

        assertEquals(5, diagram.countPountsWhereAtLeastWwoLinesOverlap())
    }


    @Test
    fun part1Task() {
        val lines = readEntries(AdventCase.Task)
        val filtered = lines.filter { it.isVertical || it.isHorizontal }
        val diagram = buildPointDiagram(filtered)

        assertEquals(6710, diagram.countPountsWhereAtLeastWwoLinesOverlap())
    }

    @Test
    fun part2Example() {
        val lines = readEntries(AdventCase.Example)

        val points1 = Line(P2(1, 1), P2(3, 3)).points()
        assertEquals(3, points1.size)
        println(points1.joinToString(","))

        val points2 = Line(P2(9, 7), P2(7, 9)).points()
        println(points2.joinToString(","))


        val diagram = buildPointDiagram(lines)
        diagram.print()

        assertEquals(12, diagram.countPountsWhereAtLeastWwoLinesOverlap())
    }

    @Test
    fun part2Task() {
        val lines = readEntries(AdventCase.Task)
        val diagram = buildPointDiagram(lines)
        assertEquals(20121, diagram.countPountsWhereAtLeastWwoLinesOverlap())
    }

    private fun readEntries(case: AdventCase): List<Line> {
        val lines = AdventDay.Day05.from2021By(case, AdventPart.Part1, AdventOrder.Order1) { _, s ->
            val (firstStr, secondStr) = s.split(" -> ")
            val p1 = firstStr.split(",").let { (x, y) -> P2(x.toInt(), y.toInt()) }
            val p2 = secondStr.split(",").let { (x, y) -> P2(x.toInt(), y.toInt()) }
            Line(p1, p2)
        }
        return lines
    }
}