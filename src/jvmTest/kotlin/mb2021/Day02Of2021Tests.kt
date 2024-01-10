package mb2021

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals


enum class MoveType(val code: String) {
    Forward("forward"),
    Down("down"),
    Up("up")
}

data class MoveCommand(val type: MoveType, val step: Int)

fun List<MoveCommand>.finishPosition(): Pair<Int, Int> {
    var x = 0
    var y = 0
    for (cmd in this) {
        when (cmd.type) {
            MoveType.Forward -> x += cmd.step
            MoveType.Up -> y -= cmd.step
            MoveType.Down -> y += cmd.step
        }
    }
    return x to y
}

fun List<MoveCommand>.finishPositionWithAim(): Pair<Int, Int> {
    var x = 0
    var y = 0
    var aim = 0
    for (cmd in this) {
        when (cmd.type) {
            MoveType.Forward -> {
                y += (cmd.step * aim)
                x += cmd.step
            }

            MoveType.Up -> aim -= cmd.step
            MoveType.Down -> aim += cmd.step
        }
    }
    return x to y
}

class Day02Of2021Tests {

    @Test
    fun part1Example() {
        val entries = readEntries(AdventCase.Example)
        assertEquals(150, entries.finishPosition().let { (x, y) -> x * y })

    }

    @Test
    fun part1Task() {
        val entries = readEntries(AdventCase.Task)
        assertEquals(2039912, entries.finishPosition().let { (x, y) -> x * y })
    }

    @Test
    fun part2Example() {
        val entries = readEntries(AdventCase.Example)
        assertEquals(900, entries.finishPositionWithAim().let { (x, y) -> x * y })
    }

    @Test
    fun part2Task() {
        val entries = readEntries(AdventCase.Task)
        assertEquals(1942068080, entries.finishPositionWithAim().let { (x, y) -> x * y })
    }

    private fun readEntries(case: AdventCase): List<MoveCommand> {
        val types = mutableMapOf<String, MoveType>(
            MoveType.Forward.code to MoveType.Forward,
            MoveType.Down.code to MoveType.Down,
            MoveType.Up.code to MoveType.Up
        )

        return AdventDay.Day02.from2021By(case, AdventPart.Part1, AdventOrder.Order1) { _, s ->
            val (typeStr, stepStr) = s.split(" ")
            MoveCommand(types[typeStr.trim()]!!, stepStr.trim().toInt())
        }
    }
}