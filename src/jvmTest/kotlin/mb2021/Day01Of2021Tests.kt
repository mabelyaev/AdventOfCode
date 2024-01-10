package mb2021

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals


fun List<Long>.largerThanThePrevious(): Int = zipWithNext().count { (a, b) ->
    a < b
}

fun List<Long>.sun3LLargerThanThePrevious(): Int = windowed(3, 1) {
    it.sum()
}.zipWithNext().count { (a, b) ->
    a < b
}


class Day01Of2021Tests {

    @Test
    fun part1Example() {
        val entries = readEntries(AdventCase.Example)
        assertEquals(7, entries.largerThanThePrevious())

    }

    @Test
    fun part1Task() {
        val entries = readEntries(AdventCase.Task)
        assertEquals(1301, entries.largerThanThePrevious())
    }

    @Test
    fun part2Example() {
        val entries = readEntries(AdventCase.Example)
        assertEquals(5, entries.sun3LLargerThanThePrevious())
    }

    @Test
    fun part2Task() {
        val entries = readEntries(AdventCase.Task)
        assertEquals(1346, entries.sun3LLargerThanThePrevious())
    }

    private fun readEntries(case: AdventCase): List<Long> =
        AdventDay.Day01.from2021By(case, AdventPart.Part1, AdventOrder.Order1) { _, s ->
            s.toLong()
        }
}