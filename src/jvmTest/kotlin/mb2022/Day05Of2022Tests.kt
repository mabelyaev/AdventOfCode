package mb2022

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals

data class Stack(val crates: MutableList<Char>)
data class Instruction(val count: Int, val from: Int, val to: Int)

fun List<Stack>.movedByInstructions(instructions: List<Instruction>): List<Stack> {
    val stacks = this.map { it.copy(it.crates.toMutableList()) }

    for (instruction in instructions) {
        val fromStack = stacks[instruction.from]
        val toStack = stacks[instruction.to]
        var countToMove = instruction.count


        while (countToMove > 0 && fromStack.crates.isNotEmpty()) {
            val crate = fromStack.crates.removeLast()
            toStack.crates.add(crate)
            countToMove--
        }
    }
    return stacks
}

fun List<Stack>.movedByInstructionsUseGroup(instructions: List<Instruction>): List<Stack> {
    val stacks = this.map { it.copy(it.crates.toMutableList()) }

    for (instruction in instructions) {
        val fromStack = stacks[instruction.from]
        val toStack = stacks[instruction.to]
        var countToMove = instruction.count

        var groupToMove = mutableListOf<Char>()
        while (countToMove > 0 && fromStack.crates.isNotEmpty()) {
            val crate = fromStack.crates.removeLast()
            groupToMove.add(0, crate)
            countToMove--
        }
        toStack.crates.addAll(groupToMove)
    }
    return stacks
}


fun List<Stack>.topOfStacks(): String {
    return this.joinToString("") { it.crates.last().toString() }
}

class Day05Of2022Tests {
    @Test
    fun part1Example() {
        val (stacks, instructions) = readGroups(AdventCase.Example)


        assertEquals(3, stacks.size)
        assertEquals(4, instructions.size)

        val newStacks = stacks.movedByInstructions(instructions)

        assertEquals("CMZ", newStacks.topOfStacks())
    }

    @Test
    fun part1Task() {
        val (stacks, instructions) = readGroups(AdventCase.Task)
        val newStacks = stacks.movedByInstructions(instructions)

        assertEquals("ZSQVCCJLL", newStacks.topOfStacks())
    }

    @Test
    fun part2Example() {
        val (stacks, instructions) = readGroups(AdventCase.Example)
        val newStacks = stacks.movedByInstructionsUseGroup(instructions)

        assertEquals("MCD", newStacks.topOfStacks())
    }

    @Test
    fun part2Task() {
        val (stacks, instructions) = readGroups(AdventCase.Task)
        val newStacks = stacks.movedByInstructionsUseGroup(instructions)

        assertEquals("QZFJRWHGS", newStacks.topOfStacks())

    }

    private fun readGroups(case: AdventCase): Pair<List<Stack>, List<Instruction>> {

        val (stackStrs, intructionStrs) =  AdventDay.Day05.parts2022By(case, AdventPart.Part1, AdventOrder.Order1, false) { it}

        var stacks = mutableListOf<Stack>()

        for (it in stackStrs) {
            if (it[1] == '1' || it[0] == '1') {
                break
            }
            //other for (j in 1 until s.length step 4) {
            it.forEachIndexed { index, c ->
                var indexOfStack = when {
                    index == 1 -> 0
                    else -> (index - 1) / 4
                }
                val stack = stacks.getOrElse(indexOfStack) {
                    Stack(mutableListOf()).also {
                        stacks.add(it)
                    }
                }
                if (c != ' ' && c != '[' && c != ']' ) {
                    stack.crates.add(0, c)
                }
            }
        }
        val regex = Regex("move (\\d+) from (\\d+) to (\\d+)")
        val intructions = intructionStrs.map {
            val onlyIndex = regex.matchEntire(it)!!.groupValues //  it.replace("move ", "").replace("from ", "").replace("to ", "").split(" ")
            Instruction(onlyIndex[1].toInt(), onlyIndex[2].toInt() - 1, onlyIndex[3].toInt() - 1)
        }


        return stacks to intructions
    }
}