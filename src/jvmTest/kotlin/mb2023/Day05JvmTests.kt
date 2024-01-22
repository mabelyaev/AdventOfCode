package mb2023

import mb.parser.*
import kotlin.math.min
import kotlin.test.Test
import kotlin.test.assertEquals


// медленная реализация
fun findDistinationNumber(
    source: String,
    distination: String,
    number: Long,
    almanac: Map<Pair<String, String>, Map<LongRange, LongRange>>
): Long {

    val entry = almanac.keys.first {
        it.first == source
    }
    val map = almanac[entry]!!

    val findedKey = map.keys.firstOrNull {
        number in it
    }

    val distinationNumber = if (findedKey == null) {
        number
    } else {
        val range = map[findedKey]!!
        val dif = number - findedKey.first
        range.first + dif
    }

    return if (entry.second == distination) {
        distinationNumber
    } else {
        findDistinationNumber(entry.second, distination, distinationNumber, almanac)
    }
}

fun findDistinationNumberWithWhile(
    source: String,
    distination: String,
    number: Long,
    almanac: Map<Pair<String, String>, Map<LongRange, LongRange>>
): Long {

    var sourceCurrent = source
    var numberCurrent = number

    while (true) {
        val entry = almanac.keys.first {
            it.first == sourceCurrent
        }
        val map = almanac[entry]!!

        val findedKey = map.keys.firstOrNull {
            numberCurrent in it
        }

        val distinationNumber = if (findedKey == null) numberCurrent else {

            val range = map[findedKey]!!
            val dif = numberCurrent - findedKey.first

            range.first + dif
        }
        if (entry.second == distination) {
            return distinationNumber
        }
        sourceCurrent = entry.second
        numberCurrent = distinationNumber
    }
}

fun findDistinationNumberChangedAlmanac(
    source: String,
    distination: String,
    number: Long,
    almanac: Map<Pair<String, String>, Map<LongRange, LongRange>>
): Long {

    val almanac2 = mutableMapOf<String, Pair<String, Map<LongRange, LongRange>>>().apply {
        almanac.forEach {
            this[it.key.first] = it.key.second to it.value
        }
    }

    var sourceCurrent = source
    var numberCurrent = number

    while (true) {
        val entry = almanac2[sourceCurrent]!!

        val findedKey = entry.second.keys.firstOrNull {
            numberCurrent in it
        }
        val distinationNumber = if (findedKey == null) numberCurrent else {

            val range = entry.second[findedKey]!!
            val dif = numberCurrent - findedKey.first

            range.first + dif
        }
        if (entry.first == distination) {
            return distinationNumber
        }
        sourceCurrent = entry.first
        numberCurrent = distinationNumber
    }
}

class Day05JvmTests {

    @Test
    fun part1Example() {
        val (seeds, almanac) = readAlmanac(AdventCase.Example)

        val locations = seeds.map {
            //findDistinationNumber("seed", "location", it, almanac)
            //findDistinationNumberWithWhile("seed", "location", it, almanac)
            findDistinationNumberChangedAlmanac("seed", "location", it, almanac)
        }

        assertEquals(35, locations.min())
    }

    @Test
    fun part1Task() {
        val (seeds, almanac) = readAlmanac(AdventCase.Task)
        val locations = seeds.map {
            findDistinationNumberChangedAlmanac("seed", "location", it, almanac)
        }

        assertEquals(484023871, locations.min())
    }

    @Test
    fun part2Example() {
        val (seeds, almanac) = readAlmanac(AdventCase.Example)

        val grouped = seeds.windowed(2, 2)

        val seedRanges = grouped.map {
            it[0]..it[0] + it[1] - 1
        }

        var minValue = Long.MAX_VALUE
        seedRanges.forEach {
            it.forEach {
                val location = findDistinationNumberChangedAlmanac("seed", "location", it, almanac)
                minValue = min(minValue, location)
            }
        }

        assertEquals(46, minValue)
    }

    @Test
    fun part2Task() {
        val (seeds, almanac) = readAlmanac(AdventCase.Task)
        val grouped = seeds.windowed(2, 2)

        val seedRanges = grouped.map {
            it[0]..it[0] + it[1] - 1
        }

        var minValue = Long.MAX_VALUE
        seedRanges.forEach {
            it.forEach {
                val location = findDistinationNumberChangedAlmanac("seed", "location", it, almanac)
                minValue = min(minValue, location)
            }
        }

        assertEquals(46294175, minValue)
    }

    private fun readAlmanac(case: AdventCase): Pair<MutableList<Long>, MutableMap<Pair<String, String>, Map<LongRange, LongRange>>> {

        var inititSeeds = mutableListOf<Long>()
        var almanac = mutableMapOf<Pair<String, String>, Map<LongRange, LongRange>>()

        AdventDay.Day05.parts2023By(case, AdventPart.Part1, AdventOrder.Order1, true) { s ->
            if (s[0].contains("seeds:")) {
                val seedNumbers = s[0].replace("seeds: ", "").split(" ").map { it.trim().toLong() }
                inititSeeds.addAll(seedNumbers)
                null
            } else {
                val tmp1 = s[0].replace(" map:", "").split("-to-")
                val source = tmp1[0].trim()
                val destination = tmp1[1].trim()
                val map = mutableMapOf<LongRange, LongRange>()
                for (i in 1..s.size - 1) {
                    val tmp2 = s[i].split(" ").map { it.trim().toLong() }
                    val distinationStart = tmp2[0]
                    val sourceStart = tmp2[1]
                    val length = tmp2[2]

                    val sourceRange = sourceStart..(sourceStart + length - 1)
                    val distinationRange = distinationStart..(distinationStart + length - 1)

                    map[sourceRange] = distinationRange

                }
                almanac[source to destination] = map
            }

        }
        return inititSeeds to almanac
    }
}
