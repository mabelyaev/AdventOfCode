package mb2022

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals


data class Handheld(val input: String) {
    fun startMarkerPositionBadVersion(countDifferentCharanter: Int): Int {
        val lastItems = mutableListOf<Char>()
        input.forEachIndexed { index, c ->
            lastItems.add(c)
            if (lastItems.size >= countDifferentCharanter){
                if (lastItems.subList(lastItems.size-countDifferentCharanter, lastItems.size).toSet().size == countDifferentCharanter) {
                    return index + 1
                } else if (lastItems.size > countDifferentCharanter) {
                    lastItems.removeAt(0)
                }
            }
        }
        throw IllegalArgumentException("dont has start position")
    }

    fun startMarkerPosition(countDifferentCharanter: Int): Int {
        for (i in 0..input.length - countDifferentCharanter) {
            val checkCharacters = input.substring(i, i+countDifferentCharanter)
            if (checkCharacters.toSet().size == countDifferentCharanter) {
                return i + countDifferentCharanter
            }
        }
        throw IllegalArgumentException("dont has start position")
    }
}
class Day06Of2022Tests {
    @Test
    fun part1Example() {
        assertEquals(7, Handheld("mjqjpqmgbljsphdztnvjfqwrcgsmlb").startMarkerPosition(4))
        assertEquals(5, Handheld("bvwbjplbgvbhsrlpgdmjqwftvncz").startMarkerPosition(4))
        assertEquals(6, Handheld("nppdvjthqldpwncqszvftbrmjlhg").startMarkerPosition(4))
        assertEquals(10, Handheld("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg").startMarkerPosition(4))

        val characters  = readChars(AdventCase.Example)
        assertEquals(11, Handheld(characters).startMarkerPosition(4))
    }

    @Test
    fun part1Task() {
        val characters  = readChars(AdventCase.Task)
        assertEquals(1034, Handheld(characters).startMarkerPosition(4))
    }

    @Test
    fun part2Example() {
        assertEquals(19, Handheld("mjqjpqmgbljsphdztnvjfqwrcgsmlb").startMarkerPosition(14))
        assertEquals(23, Handheld("bvwbjplbgvbhsrlpgdmjqwftvncz").startMarkerPosition(14))
        assertEquals(23, Handheld("nppdvjthqldpwncqszvftbrmjlhg").startMarkerPosition(14))
        assertEquals(29, Handheld("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg").startMarkerPosition(14))

        val characters  = readChars(AdventCase.Example)
        assertEquals(26, Handheld(characters).startMarkerPosition(14))
    }

    @Test
    fun part2Task() {
        val characters  = readChars(AdventCase.Task)
        assertEquals(2472, Handheld(characters).startMarkerPosition(14))
    }

    private fun readChars(case: AdventCase): String {
        return AdventDay.Day06.from2022By(case, AdventPart.Part1, AdventOrder.Order1) { _, s -> s}.first()
    }
}