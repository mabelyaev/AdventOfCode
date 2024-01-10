package mb2020

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals

fun count(rows: List<String>, right: Int, down: Int = 1): Int {
    var countOfTree = 0
    var xIndex = 0

    val lengthOfRow = rows.first().length

    for (yIndex in rows.indices) {
        if (yIndex != 0 && yIndex % down == 0) {
            xIndex += right
            val rIndex = xIndex % lengthOfRow
            val s = rows[yIndex].getOrNull(rIndex)
                ?: throw IllegalStateException("cant find element in positoin x = ${rIndex}, y = ${yIndex}")

            if (s == '#') {
                countOfTree++
            }
        }
    }

    return countOfTree
}

class Day03Of2020Tests {

    @Test
    fun part1Example() {
        val entries = readEntries(AdventCase.Example)
        assertEquals(7, count(entries, 3, 1))
    }

    @Test
    fun part1Task() {
        val entries = readEntries(AdventCase.Task)
        assertEquals(193, count(entries, 3, 1))
    }

    @Test
    fun part2Example() {
        val entries = readEntries(AdventCase.Example)

        val trees = listOf(
            count(entries, 1, 1),
            count(entries, 3, 1),
            count(entries, 5, 1),
            count(entries, 7, 1),
            count(entries, 1, 2),
        )
        assertEquals(336, trees.fold(1) { acc, i -> acc * i })
    }

    @Test
    fun part2Task() {
        val entries = readEntries(AdventCase.Task)

        val trees = listOf(
            count(entries, 1, 1),
            count(entries, 3, 1),
            count(entries, 5, 1),
            count(entries, 7, 1),
            count(entries, 1, 2),
        )
        assertEquals(1355323200, trees.fold(1) { acc, i -> acc * i })
    }

    private fun readEntries(case: AdventCase): List<String> =
        AdventDay.Day03.from2020By(case, AdventPart.Part1, AdventOrder.Order1) { _, s -> s }
}