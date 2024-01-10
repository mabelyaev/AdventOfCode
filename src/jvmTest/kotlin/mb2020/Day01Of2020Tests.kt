package mb2020

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals


fun List<Long>.pairSumTo(sum: Long): Pair<Long, Long> {
    for (i in indices) {
        for (j in indices) {
            if (i != j && this[i] + this[j] == sum) {
                return this[i] to this[j]
            }
        }
    }
    throw IllegalStateException("dont have two entries that sum to ${sum}")
}

fun List<Long>.tripleSumTo(sum: Long): Triple<Long, Long, Long> {
    for (i in indices) {
        for (j in indices) {
            for (z in indices) {
                if (i != j && i != z && j != z && this[i] + this[j] + this[z] == sum) {
                    return Triple(this[i], this[j], this[z])
                }
            }
        }
    }
    throw IllegalStateException("dont have two entries that sum to ${sum}")
}

class Day01Of2020Tests {

    @Test
    fun part1Example() {
        val entries = readEntries(AdventCase.Example)
        val (a, b) = entries.pairSumTo(2020)
        assertEquals(1721, a)
        assertEquals(299, b)
        assertEquals(514579, a * b)
    }

    @Test
    fun part1Task() {
        val entries = readEntries(AdventCase.Task)
        val (a, b) = entries.pairSumTo(2020)
        assertEquals(1014624, a * b)
    }

    @Test
    fun part2Example() {
        val entries = readEntries(AdventCase.Example)
        val (a, b, c) = entries.tripleSumTo(2020)
        assertEquals(241861950, a * b * c)
    }

    @Test
    fun part2Task() {
        val entries = readEntries(AdventCase.Task)
        val (a, b, c) = entries.tripleSumTo(2020)
        assertEquals(80072256, a * b * c)
    }

    private fun readEntries(case: AdventCase): List<Long> =
        AdventDay.Day01.from2020By(case, AdventPart.Part1, AdventOrder.Order1) { _, s ->
            s.toLong()
        }
}