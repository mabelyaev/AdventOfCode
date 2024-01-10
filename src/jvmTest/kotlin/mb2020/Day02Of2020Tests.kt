package mb2020

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals

data class PasswordItem(val mask: Char, val range: IntRange, val password: String) {
    fun isValidInRange(): Boolean = password.count { it == mask } in range
    fun isValidPositionOnlyOnce(): Boolean {
        val firstLetter = password.get(range.start - 1)
        val secondLetter = password.get(range.endInclusive - 1)

        return firstLetter != secondLetter && (firstLetter == mask || secondLetter == mask)
    }
}

class Day02Of2020Tests {

    @Test
    fun part1Example() {
        val entries = readEntries(AdventCase.Example)
        assertEquals(2, entries.count { it.isValidInRange() })
    }

    @Test
    fun part1Task() {
        val entries = readEntries(AdventCase.Task)
        assertEquals(517, entries.count { it.isValidInRange() })
    }

    @Test
    fun part2Example() {
        val entries = readEntries(AdventCase.Example)
        assertEquals(1, entries.count { it.isValidPositionOnlyOnce() })
    }

    @Test
    fun part2Task() {
        val entries = readEntries(AdventCase.Task)
        assertEquals(284, entries.count { it.isValidPositionOnlyOnce() })

    }

    private fun readEntries(case: AdventCase): List<PasswordItem> =
        AdventDay.Day02.from2020By(case, AdventPart.Part1, AdventOrder.Order1) { _, s ->
            val (rule, password) = s.split(":")
            val (rangeStr, mask) = rule.split(" ")
            val range = rangeStr.split("-").let { (start, end) ->
                start.trim().toInt()..end.trim().toInt()
            }

            PasswordItem(mask.trim().first(), range, password.trim())
        }
}