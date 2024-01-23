package mb2021

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals

data class Lanternfish(var timerForBorn: Int) {
    fun newDay(): Lanternfish? {
        timerForBorn--
        if (timerForBorn < 0) {
            timerForBorn = 6
            return Lanternfish(8)
        }
        return null
    }
}

data class SchoolOfFish(val daysAndFishBeforeBorn: LongArray) {

    var day = 0
    var countWillBorn = 0L

    private var tempArray: LongArray = LongArray(9)
    fun newDay() {
        day++

        tempArray.fill(0)
        daysAndFishBeforeBorn.forEachIndexed { index, count ->
            val newIndex = if (index - 1 < 0) 6 else index - 1
            tempArray[newIndex] = tempArray[newIndex] + count
            if (index - 1 < 0) {
                countWillBorn = count
            }
        }
        tempArray[8] = countWillBorn
        tempArray.forEachIndexed { index, l ->
            daysAndFishBeforeBorn[index] = l
        }
        //println("${day} : ${daysAndFishBeforeBorn.joinToString(",")}")
    }
}

class Day06Of2021Tests {

    @Test
    fun part1Example() {
        val schoolOfFish = readEntries(AdventCase.Example)

        assertEquals(5, schoolOfFish.daysAndFishBeforeBorn.sum())

        while (schoolOfFish.day != 18) {
            schoolOfFish.newDay()
        }
        assertEquals(26, schoolOfFish.daysAndFishBeforeBorn.sum())


        while (schoolOfFish.day != 80) {
            schoolOfFish.newDay()
        }
        assertEquals(5934, schoolOfFish.daysAndFishBeforeBorn.sum())
    }


    @Test
    fun part1Task() {
        val schoolOfFish = readEntries(AdventCase.Task)
        while (schoolOfFish.day != 80) {
            schoolOfFish.newDay()
        }
        assertEquals(353274, schoolOfFish.daysAndFishBeforeBorn.sum())
    }


    @Test
    fun part2Example() {
        val schoolOfFish = readEntries(AdventCase.Example)

        while (schoolOfFish.day != 256) {
            schoolOfFish.newDay()
        }
        assertEquals(26984457539, schoolOfFish.daysAndFishBeforeBorn.sum())
    }

    @Test
    fun part2Task() {
        val schoolOfFish = readEntries(AdventCase.Task)
        while (schoolOfFish.day != 256) {
            schoolOfFish.newDay()
        }
        assertEquals(1609314870967, schoolOfFish.daysAndFishBeforeBorn.sum())
    }

    private fun readEntries(case: AdventCase): SchoolOfFish {
        val daysAndCountOfFish = AdventDay.Day06.from2021By(case, AdventPart.Part1, AdventOrder.Order1) { _, s ->
            s
        }.first().split(",").map { it.toInt() }.let {
            val tmp = LongArray(9) { 0 }
            it.forEach { n ->
                tmp[n] += 1L
            }
            tmp
        }
        return SchoolOfFish(daysAndCountOfFish)
    }
}