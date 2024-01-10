package mb2023

import mb.base.ColorOfShape
import mb.parser.*
import kotlin.math.max
import kotlin.test.Test
import kotlin.test.assertEquals

data class SetPair(val count: Int, val color: ColorOfShape)
data class GameBag(val limitRed: Int, val limitGreen: Int, val limitBlue: Int)
data class GameItem(val id: Long, val sets: List<List<SetPair>>)

fun checkSet(bag: GameBag, pairs: List<SetPair>): Boolean {
    return pairs.all {
        when (it.color) {
            ColorOfShape.Red -> it.count <= bag.limitRed
            ColorOfShape.Green -> it.count <= bag.limitGreen
            ColorOfShape.Blue -> it.count <= bag.limitBlue
        }
    }
}

fun minSetNeed(sets: List<List<SetPair>>): List<SetPair> {
    val result = mutableMapOf<ColorOfShape, Int>()

    sets.forEach {
        it.forEach {
            val maxValue = result.getOrPut(it.color) { it.count }
            result[it.color] = max(it.count, maxValue)
        }
    }
    return result.map {
        SetPair(it.value, it.key)
    }
}

class Day02Of2023Tests {

    @Test
    fun part1Example() {
        val games = readGames(AdventCase.Example)

        val bag = GameBag(12, 13, 14)

        val validGames = games.filter { g ->
            g.sets.all {
                checkSet(bag, it)

            }
        }
        val sumOfValidGame = validGames.map { it.id }.sum()
        assertEquals(8, sumOfValidGame)
    }

    @Test
    fun part1Task() {
        val games = readGames(AdventCase.Task)
        assertEquals(100, games.size)


        val checker = GameBag(12, 13, 14)

        val validGames = games.filter { g ->
            g.sets.all {
                checkSet(checker, it)

            }
        }
        val sumOfValidGame = validGames.map { it.id }.sum()
        assertEquals(2268, sumOfValidGame)
    }

    @Test
    fun part2Example() {
        val games = readGames(AdventCase.Example)


        val minSets = games.map {
            minSetNeed(it.sets)
        }

        val powers = minSets.map {
            it.map { it.count }.fold(1) { acc, elm -> acc * elm }
        }

        assertEquals(2286, powers.sum())
    }

    @Test
    fun part2Task() {
        val games = readGames(AdventCase.Task)

        val minSets = games.map {
            minSetNeed(it.sets)
        }

        val powers = minSets.map {
            it.map { it.count }.fold(1) { acc, elm -> acc * elm }
        }

        assertEquals(63542, powers.sum())
    }

    private fun readGames(case: AdventCase): List<GameItem> {
        var games = AdventDay.Day02.from2023By(case, AdventPart.Part1, AdventOrder.Order1) { index, s ->
            val gameId = index + 1L
            val sets = s.substringAfter("Game ${gameId}: ").split(";").map {
                it.split(",").map { strPair ->
                    val (countStr, colorStr) = strPair.trim().split(" ")
                    SetPair(
                        countStr.trim().toInt(),
                        ColorOfShape.values().first { it.nameStr == colorStr }
                    )
                }
            }
            GameItem(gameId, sets)
        }
        return games
    }

}