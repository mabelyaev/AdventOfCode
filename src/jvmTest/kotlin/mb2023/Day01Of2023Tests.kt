package mb2023

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals


fun List<String>.firstAndLastDigits(): List<Long> {
    return map {
        val digits = it.filter { it.isDigit() }
        "${digits.first()}${digits.last()}".toLong()
    }
}

fun List<String>.firstAndLastDigitsWithDigitNames(): List<Long> {
    val digitNames = listOf("", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    return map { s ->

        val digits = mutableListOf<Long>()
        for (i in s.indices) {
            when {
                s[i].isDigit() -> digits.add("${s[i]}".toLong())
                else -> {
                    val currentStr = s.substring(i)

                    digitNames.forEachIndexed { index, digitName ->
                        if (index > 0 && currentStr.startsWith(digitName)) {
                            digits.add(index.toLong())
                        }
                    }
                }
            }
        }
        "${digits.first()}${digits.last()}".toLong()
    }
}

class Day01Of2022Tests {

    @Test
    fun part1Example() {
        val brokenValues = readCalibrationValues(AdventCase.Example, AdventPart.Part1)
        assertEquals(142, brokenValues.firstAndLastDigits().sum())
    }

    @Test
    fun part1Task() {
        val brokenValues = readCalibrationValues(AdventCase.Task, AdventPart.Part1)
        assertEquals(54390, brokenValues.firstAndLastDigits().sum())

    }

    @Test
    fun part2Example() {
        val brokenValues = readCalibrationValues(AdventCase.Example, AdventPart.Part2)
        assertEquals(281, brokenValues.firstAndLastDigitsWithDigitNames().sum())
    }

    @Test
    fun part2Task() {
        val brokenValues = readCalibrationValues(AdventCase.Task, AdventPart.Part1)
        assertEquals(54277, brokenValues.firstAndLastDigitsWithDigitNames().sum())
    }

    private fun readCalibrationValues(case: AdventCase, part: AdventPart): List<String> =
        AdventDay.Day01.from2023By(case, part, AdventOrder.Order1) { i, s ->
            s
        }
}