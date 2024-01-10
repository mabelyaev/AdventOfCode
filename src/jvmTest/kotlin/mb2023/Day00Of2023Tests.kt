package mb2023

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class Day00Of2023Tests {
    @Test
    fun example1() {
        val input = read(AdventCase.Example)

        assertNotNull(input)
        assertEquals(3, input.size)
    }

    @Test
    fun task1() {
        val input = read(AdventCase.Task)

        assertNotNull(input)
        assertEquals(7, input.size)
    }

    @Test
    fun example2() {
        val input = read(AdventCase.Example)

        assertNotNull(input)
        assertEquals(3, input.size)
    }

    @Test
    fun task2() {
        val input = read(AdventCase.Task)

        assertNotNull(input)
        assertEquals(7, input.size)
    }

    private fun read(case: AdventCase): List<String> {
        return AdventDay.Day00.from2023By(case, AdventPart.Part1, AdventOrder.Order1) { _, s ->
            s
        }
    }

}