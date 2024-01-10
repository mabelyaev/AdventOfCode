package mb2021

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals

fun toCommonBitStringBy(entries: List<List<Int>>, mostCommon: Boolean): String {
    val length = entries.first().size
    val s = buildString {
        for (x in 0..length - 1) {
            var zeroCount = 0
            var oneCount = 0
            for (y in entries.indices) {
                val number = entries[y][x]
                when (number) {
                    0 -> zeroCount++
                    1 -> oneCount++
                    else -> throw IllegalStateException("wrong number in position x:${x}, y:${y}")
                }
            }
            if (mostCommon) {
                append(if (zeroCount > oneCount) "0" else "1")
            } else {
                append(if (zeroCount < oneCount) "0" else "1")
            }
        }
    }
    return s
}

fun toCommonBitOneByOneStringBy(entries: List<List<Int>>, mostCommon: Boolean): String {
    val length = entries.first().size

    var zeroBits = Array<Boolean>(entries.size) { true }
    var oneBits = Array<Boolean>(entries.size) { true }

    for (x in 0..length - 1) {
        var zeroCount = 0
        var oneCount = 0
        for (y in entries.indices) {
            if (zeroBits[y] == true) {
                val number = entries[y][x]
                when (number) {
                    0 -> {
                        zeroCount++
                        oneBits[y] = false
                    }

                    1 -> {
                        oneCount++
                        zeroBits[y] = false
                    }

                    else -> throw IllegalStateException("wrong number in position x:${x}, y:${y}")
                }
            }
        }
        if (mostCommon) {
            if (zeroCount > oneCount) {
                oneBits = zeroBits.clone()
            } else {
                zeroBits = oneBits.clone()
            }
        } else {
            if (zeroCount <= oneCount) {
                oneBits = zeroBits.clone()
            } else {
                zeroBits = oneBits.clone()
            }
        }
        val countRows = oneBits.count { it }
        if (countRows == 1) {
            return entries[oneBits.indexOfFirst { it }].joinToString("")
        }
    }

    throw IllegalStateException("cant find row")
}


class Day03Of2021Tests {

    @Test
    fun part1Example() {
        val entries = readEntries(AdventCase.Example)

        val mostCommon = toCommonBitStringBy(entries, true)
        val leastCommon = toCommonBitStringBy(entries, false)

        assertEquals(198, mostCommon.toInt(2) * leastCommon.toInt(2))
    }


    @Test
    fun part1Task() {
        val entries = readEntries(AdventCase.Task)
        val mostCommon = toCommonBitStringBy(entries, true)
        val leastCommon = toCommonBitStringBy(entries, false)

        assertEquals(1071734, mostCommon.toInt(2) * leastCommon.toInt(2))
    }

    @Test
    fun part2Example() {
        val entries = readEntries(AdventCase.Example)

        val mostCommon = toCommonBitOneByOneStringBy(entries, true)
        val leastCommon = toCommonBitOneByOneStringBy(entries, false)

        assertEquals(230, mostCommon.toInt(2) * leastCommon.toInt(2))
    }

    @Test
    fun part2Task() {
        val entries = readEntries(AdventCase.Task)
        val mostCommon = toCommonBitOneByOneStringBy(entries, true)
        val leastCommon = toCommonBitOneByOneStringBy(entries, false)

        assertEquals(6124992, mostCommon.toInt(2) * leastCommon.toInt(2))
    }

    private fun readEntries(case: AdventCase): List<List<Int>> {
        return AdventDay.Day03.from2021By(case, AdventPart.Part1, AdventOrder.Order1) { _, s ->
            s.map { it.digitToInt() }
        }
    }
}