package mb2023

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals

data class TimeAndDistance(val time: Long, val distance: Long)

fun TimeAndDistance.foldToWins(): List<TimeAndDistance> {

    return (1..time).mapNotNull { currentTime ->
        distanceForWinOrNUll(time, currentTime, distance)
    }
}

fun TimeAndDistance.countWins(): Int {
    return (1..time).count { currentTime ->
        distanceForWinOrNUll(time, currentTime, distance) != null
    }
}

private fun distanceForWinOrNUll(
    timeRace: Long,
    currentTime: Long,
    minimumDistance: Long
): TimeAndDistance? {
    val timeToTravel = timeRace - currentTime
    val distance = timeToTravel * currentTime
    return if (distance > minimumDistance) {
        TimeAndDistance(currentTime, distance)
    } else {
        null
    }
}


fun List<TimeAndDistance>.toOneRace(): TimeAndDistance {
    var timeStr = ""
    var distanceStr = ""

    this.forEach {
        timeStr += it.time
        distanceStr += it.distance
    }

    return TimeAndDistance(timeStr.toLong(), distanceStr.toLong())
}


class Day06JvmTests {

    @Test
    fun part1Example() {
        val races = readRaces(AdventCase.Example)

        val winCases = races.map { it.foldToWins() }


        val multiplied = winCases.map { it.size }.fold(1) { acc, v -> acc * v }
        assertEquals(288, multiplied)
    }

    @Test
    fun part1Task() {
        val races = readRaces(AdventCase.Task)
        val winCases = races.map { it.foldToWins() }


        val multiplied = winCases.map { it.size }.fold(1) { acc, v -> acc * v }
        assertEquals(138915, multiplied)
    }

    @Test
    fun part2Example() {
        val races = readRaces(AdventCase.Example)

        val race = races.toOneRace()
        assertEquals(71503, race.countWins())
    }

    @Test
    fun part2Task() {
        val races = readRaces(AdventCase.Task)

        val race = races.toOneRace()
        assertEquals(27340847, race.countWins())
    }

    private fun readRaces(case: AdventCase): List<TimeAndDistance> {
        val lines = AdventDay.Day06.from2023By(case, AdventPart.Part1, AdventOrder.Order1) { _, s -> s }

        val times = lines[0].replace("Time: ", "").trim().split(" ")
            .mapNotNull { it.trim().takeIf { it.isNotEmpty() }?.toLong() }
        val distances = lines[1].replace("Distance: ", "").trim().split(" ")
            .mapNotNull { it.trim().takeIf { it.isNotEmpty() }?.toLong() }

        return times.mapIndexed { index, t ->
            TimeAndDistance(t, distances[index])
        }
    }
}
