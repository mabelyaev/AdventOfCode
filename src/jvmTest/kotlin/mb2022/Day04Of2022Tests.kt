package mb2022

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals


data class CleaningGroup(
    val first: IntRange,
    val second: IntRange,
) {
    fun oneGroupInsideOther() : Boolean {
        val firstList = first.toList()
        val secondList = second.toList()
        return firstList.intersect(secondList).size == firstList.size || secondList.intersect(firstList).size == secondList.size
    }

    fun hasOverlap() : Boolean {
        return first.intersect(second).size > 0
    }
}

class Day04Of2022Tests {
    @Test
    fun part1Example() {
        val groups = readGroups(AdventCase.Example)
        assertEquals(6, groups.size)
        assertEquals(listOf(2,3,4), groups.first().first.toList())
        assertEquals(listOf(6,7,8), groups.first().second.toList())

        val groupsWithOneRangeFullyContainOther = groups.filter { it.oneGroupInsideOther() }
        assertEquals(2, groupsWithOneRangeFullyContainOther.size)
    }

    @Test
    fun part1Task() {
        val groups = readGroups(AdventCase.Task)
        val groupsWithOneRangeFullyContainOther = groups.filter { it.oneGroupInsideOther() }
        assertEquals(462, groupsWithOneRangeFullyContainOther.size)
    }

    @Test
    fun part2Example() {
        val groups = readGroups(AdventCase.Example)
        val groupsWithOverlap = groups.filter { it.hasOverlap() }
        assertEquals(4, groupsWithOverlap.size)
    }

    @Test
    fun part2Task() {
        val groups = readGroups(AdventCase.Task)
        val groupsWithOverlap = groups.filter { it.hasOverlap() }
        assertEquals(835, groupsWithOverlap.size)
    }

    private fun readGroups(case: AdventCase): List<CleaningGroup> {
        var groups = AdventDay.Day04.from2022By(case, AdventPart.Part1, AdventOrder.Order1) { _, s ->
           val (first, second ) = s.split(",").map { range ->
               val (start, end) =  range.split("-")
               IntRange(start.toInt(), end.toInt())
           }
            CleaningGroup(first, second)
        }
        return groups
    }
}