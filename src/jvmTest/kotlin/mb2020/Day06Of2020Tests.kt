package mb2020

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals

data class TestedGroup( val userAnswers: List<List<Char>>) {
    fun anyYesAnswered()  : Set<Char> = mutableSetOf<Char>().apply {
        for(answers in userAnswers) {
            this.addAll(answers)
        }
    }

    fun allYesAnswered() : Set<Char>  {
        var tempSet = userAnswers.first().toSet()
        userAnswers.forEach {
            tempSet = tempSet.intersect(it)
        }
        return tempSet
    }
}

class Day06Of2020Tests {

    @Test
    fun part1Example() {
        val entries = readEntries(AdventCase.Example)
        val count = entries.sumOf {
            it.anyYesAnswered().size
        }
        assertEquals(11, count)
    }

    @Test
    fun part1Task() {
        val entries = readEntries(AdventCase.Task)
        val count = entries.sumOf {
            it.anyYesAnswered().size
        }
        assertEquals(6351, count)
    }

    @Test
    fun part2Example() {
        val entries = readEntries(AdventCase.Example)
        val count = entries.sumOf {
            it.allYesAnswered().size
        }
        assertEquals(6, count)
    }

    @Test
    fun part2Task() {
        val entries = readEntries(AdventCase.Task)
        val count = entries.sumOf {
            println("${it.allYesAnswered()}")
            it.allYesAnswered().size
        }
        assertEquals(3143, count)
    }

    private fun readEntries(case: AdventCase, order: AdventOrder = AdventOrder.Order1): List<TestedGroup> =
        AdventDay.Day06.parts2020By(case, AdventPart.Part1, order, true) { lines ->
            TestedGroup(
                lines.map { it.toList() }
            )
        }
}