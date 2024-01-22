package mb2022

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals

data class PackOfElve(val number: Int, val caloriesOfFood: List<Int>) {
    fun allCalories(): Int = caloriesOfFood.sum()
}

fun List<PackOfElve>.maxCalories() = this.maxBy { it.allCalories() }
fun List<PackOfElve>.takeTopByCalories(count: Int) = this.sortedByDescending { it.allCalories() }.take(count)

class Day01Of2022Tests {

    @Test
    fun part1Example() {
        val packs = readPacks(AdventCase.Example)
        assertEquals(24000, packs.maxCalories().allCalories())
    }

    @Test
    fun part1Task() {
        val packs = readPacks(AdventCase.Task)
        assertEquals(69310, packs.maxCalories().allCalories())
    }

    @Test
    fun part2Example() {
        val packs = readPacks(AdventCase.Example)
        assertEquals(45000, packs.takeTopByCalories(3).sumOf { it.allCalories() })
    }

    @Test
    fun part2Task() {
        val packs = readPacks(AdventCase.Task)
        assertEquals(206104, packs.takeTopByCalories(3).sumOf { it.allCalories() })
    }

    private fun readPacks(case: AdventCase): List<PackOfElve> {
        var groupNumber = 1
        var rounds = AdventDay.Day01.parts2022By(case, AdventPart.Part1, AdventOrder.Order1, true) { s ->
            PackOfElve(groupNumber++, s.map { it.toInt() })
        }
        return rounds
    }
}