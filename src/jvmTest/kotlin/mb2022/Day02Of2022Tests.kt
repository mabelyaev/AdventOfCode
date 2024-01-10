package mb2022

import mb.base.GameResult
import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals

enum class HandShape {
    Rock,
    Paper,
    Scissors
}

fun HandShape.loseShape(): HandShape = when (this) {
    HandShape.Rock -> HandShape.Scissors
    HandShape.Scissors -> HandShape.Paper
    HandShape.Paper -> HandShape.Rock
}

enum class HandShapeOfElve(val shape: HandShape) {
    A(HandShape.Rock),
    B(HandShape.Paper),
    C(HandShape.Scissors),
}

enum class CodeForSecond(val shape: HandShape, val gameResult: GameResult) {
    X(HandShape.Rock, GameResult.Lose),
    Y(HandShape.Paper, GameResult.Draw),
    Z(HandShape.Scissors, GameResult.Won),
}

enum class HandShapePlayers {
    Elve,
    Santa
}

data class HandShapeRound(val first: HandShapeOfElve, val second: CodeForSecond) {
    fun winnerByRole1(): HandShapePlayers? {
        return when {
            first.shape == second.shape -> null
            first.shape.loseShape() == second.shape -> HandShapePlayers.Elve
            else -> HandShapePlayers.Santa
        }
    }

    fun winnerByRole2(): HandShapePlayers? {
        return when (second.gameResult) {
            GameResult.Lose -> HandShapePlayers.Elve
            GameResult.Won -> HandShapePlayers.Santa
            GameResult.Draw -> null
        }
    }
}

fun List<HandShapeRound>.calcSantaScore1(): Int {
    return this.sumOf {
        val shapeScope = when (it.second.shape) {
            HandShape.Rock -> 1
            HandShape.Paper -> 2
            HandShape.Scissors -> 3
        }
        val roundScope = when (it.winnerByRole1()) {
            HandShapePlayers.Elve -> 0
            HandShapePlayers.Santa -> 6
            null -> 3
        }
        shapeScope + roundScope
    }
}

fun List<HandShapeRound>.calcSantaScore2(): Int {
    return this.sumOf {
        val santaShape = when (it.second.gameResult) {
            GameResult.Draw -> it.first.shape
            GameResult.Lose -> it.first.shape.loseShape()
            GameResult.Won -> it.first.shape.loseShape().loseShape()
        }
        val shapeScope = when (santaShape) {
            HandShape.Rock -> 1
            HandShape.Paper -> 2
            HandShape.Scissors -> 3
        }
        val roundScope = when (it.winnerByRole2()) {
            HandShapePlayers.Elve -> 0
            HandShapePlayers.Santa -> 6
            null -> 3
        }
        shapeScope + roundScope
    }
}

class Day02Of2022Tests {

    @Test
    fun example1() {
        val rounds = readShapeCommands(AdventCase.Example)
        assertEquals(15, rounds.calcSantaScore1())
    }

    @Test
    fun task1() {
        val rounds = readShapeCommands(AdventCase.Task)
        assertEquals(14264, rounds.calcSantaScore1())
    }

    @Test
    fun handShapeScopesExample2() {
        val rounds = readShapeCommands(AdventCase.Example)
        assertEquals(12, rounds.calcSantaScore2())
    }

    @Test
    fun handShapeScopes2() {
        val rounds = readShapeCommands(AdventCase.Task)
        assertEquals(12382, rounds.calcSantaScore2())
    }

    private fun readShapeCommands(case: AdventCase): List<HandShapeRound> {
        var rounds = AdventDay.Day02.from2022By(case, AdventPart.Part1, AdventOrder.Order1) { _, s ->
            val (firstCmd, secondCmd) = s.split(" ")
            HandShapeRound(HandShapeOfElve.valueOf(firstCmd), CodeForSecond.valueOf(secondCmd))
        }
        return rounds
    }
}