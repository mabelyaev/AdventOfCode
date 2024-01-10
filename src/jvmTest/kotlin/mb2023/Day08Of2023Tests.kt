package mb2023

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals


enum class NavigateInstruction {
    L,
    R
}

data class TmpStep(
    val from: String,
    val toLocation: String,
    val positionInInstruction: Int
)

fun lengthOfPath(
    from: String,
    toLocation: String,
    instructions: List<NavigateInstruction>,
    map: Map<String, Pair<String, String>>
): Int {
    var count = 0
    var fromLocatrion = from
    var nextPosition = 0

    while (fromLocatrion != toLocation) {
        val currentCross = map[fromLocatrion] ?: throw IllegalStateException("${fromLocatrion} in ${nextPosition}")
        val instruction = instructions.getOrNull(nextPosition) ?: instructions[0]

        fromLocatrion = if (instruction == NavigateInstruction.L) currentCross.first else currentCross.second
        nextPosition = if (nextPosition + 1 < instructions.size) nextPosition + 1 else 0
        count++

    }
    return count
}

data class PathAndCheckedEnd(val code: String, val leftCode: String, val rightCode: String, val sameEnd: Boolean)

fun lengthOfPathForEndOfWord(
    from: List<String>,
    toLocationEndOfWord: String,
    instructions: List<NavigateInstruction>,
    map: Map<String, Pair<String, String>>
): Int {
    var count = 0

    val mapWithEndMarker: Map<String, PathAndCheckedEnd> = mutableMapOf<String, PathAndCheckedEnd>().apply {
        map.forEach {
            put(
                it.key,
                PathAndCheckedEnd(it.key, it.value.first, it.value.second, it.key.endsWith(toLocationEndOfWord))
            )
        }
    }

    val fromLocation: Array<String> = from.toTypedArray()

    var nextPosition = 0

    var allSame = false


    while (allSame == false) {
        val instrunction = instructions.getOrNull(nextPosition) ?: instructions[0]
        allSame = true
        for (i in 0..from.size - 1) {
            val currentCross =
                mapWithEndMarker[fromLocation[i]] ?: throw IllegalStateException("${i} in ${nextPosition}")
            val nextPoint = if (instrunction == NavigateInstruction.L) currentCross.leftCode else currentCross.rightCode
            fromLocation[i] = nextPoint
            allSame = allSame && (mapWithEndMarker[nextPoint]
                ?: throw IllegalStateException("${i} in ${nextPosition}")).sameEnd
        }
        nextPosition = if (nextPosition + 1 < instructions.size) nextPosition + 1 else 0
        count++

    }
    return count
}

class Day08Of2023Tests {

    @Test
    fun example1_1() {
        val (instructions, map) = readInstructionsAndMap(AdventCase.Example)
        assertEquals(2, lengthOfPath("AAA", "ZZZ", instructions, map))
    }

    @Test
    fun example1_2() {
        val (instructions, map) = readInstructionsAndMap(AdventCase.Example, order = AdventOrder.Order2)
        assertEquals(6, lengthOfPath("AAA", "ZZZ", instructions, map))
    }

    @Test
    fun task1() {
        val (instructions, map) = readInstructionsAndMap(AdventCase.Task)
        assertEquals(16531, lengthOfPath("AAA", "ZZZ", instructions, map))
    }

    @Test
    fun example2() {
        val (instructions, map) = readInstructionsAndMap(AdventCase.Example, AdventPart.Part2)
        val startPoints = map.keys.filter { it.endsWith("A") }
        assertEquals(6, lengthOfPathForEndOfWord(startPoints, "Z", instructions, map))
    }

    @Test
    fun task2() {
        val (instructions, map) = readInstructionsAndMap(AdventCase.Task)
        val startPoints = map.keys.filter { it.endsWith("A") }
        assertEquals(6, lengthOfPathForEndOfWord(startPoints, "Z", instructions, map))
    }

    private fun readInstructionsAndMap(
        case: AdventCase,
        part: AdventPart = AdventPart.Part1,
        order: AdventOrder = AdventOrder.Order1
    ): Pair<List<NavigateInstruction>, Map<String, Pair<String, String>>> {
        val (inst, paths) = AdventDay.Day08.groupFrom2023By(case, part, order, 2)

        val instructions = inst.first().map { NavigateInstruction.valueOf(it.toString()) }
        val map = paths.map { s ->
            val (point, pathStr) = s.split(" = ").map { it.trim() }
            val (left, right) = pathStr.removeSurrounding("(", ")").split(",").map { it.trim() }

            Pair(point, Pair(left, right))
        }.toMap()
        return instructions to map
    }

}