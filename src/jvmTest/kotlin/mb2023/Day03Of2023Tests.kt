package mb2023

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals


data class P2(val x: Int, val y: Int) {
    fun nearestPoints(): List<P2> {
        val it = this
        return listOf(
            P2(it.x - 1, it.y),
            P2(it.x + 1, it.y),
            P2(it.x, it.y - 1),
            P2(it.x, it.y + 1),
            P2(it.x + 1, it.y + 1),
            P2(it.x + 1, it.y - 1),
            P2(it.x - 1, it.y + 1),
            P2(it.x - 1, it.y - 1)
        )
    }
}

data class NumberWithPositions(val number: Long, val positions: List<P2>) {
    fun hasNearSymbols(symbols: Map<P2, Char>): Boolean {
        return positions.any {
            symbols.containsKey(P2(it.x - 1, it.y)) ||
                    symbols.containsKey(P2(it.x + 1, it.y)) ||
                    symbols.containsKey(P2(it.x, it.y - 1)) ||
                    symbols.containsKey(P2(it.x, it.y + 1)) ||
                    symbols.containsKey(P2(it.x + 1, it.y + 1)) ||
                    symbols.containsKey(P2(it.x + 1, it.y - 1)) ||
                    symbols.containsKey(P2(it.x - 1, it.y + 1)) ||
                    symbols.containsKey(P2(it.x - 1, it.y - 1))
        }
    }

    fun hasNearSymbol(p: P2): Boolean {
        return positions.any {
            p.nearestPoints().contains(it)
        }
    }


}

private fun gears(
    gears: Map<P2, Char>,
    numbers: MutableList<NumberWithPositions>
): List<Long> {
    val gearRatios = gears.mapNotNull {
        val nearestPoints = it.key.nearestPoints()
        val n = numbers.filter {
            it.positions.any {
                nearestPoints.contains(it)
            }
        }
        if (n.size == 2) {
            n[0].number * n[1].number
        } else {
            null
        }
    }
    return gearRatios
}


class Day03Of2023Tests {

    @Test
    fun part1Example() {
        val (symbols, numbers) = readMap(AdventCase.Example)
        val filtered = numbers.filter {
            it.hasNearSymbols(symbols)
        }
        assertEquals(4361, filtered.map { it.number }.sum())
    }

    @Test
    fun part1Task() {
        val (symbols, numbers) = readMap(AdventCase.Task)
        val filtered = numbers.filter {
            it.hasNearSymbols(symbols)
        }
        assertEquals(514969, filtered.map { it.number }.sum())
    }

    @Test
    fun part2Example() {
        val (symbols, numbers) = readMap(AdventCase.Example)
        val gears = symbols.filter { it.value == '*' }
        val gearRatios = gears(gears, numbers)
        assertEquals(467835, gearRatios.sum())
    }


    @Test
    fun part2Task() {
        val (symbols, numbers) = readMap(AdventCase.Task)
        val gears = symbols.filter { it.value == '*' }
        val gearRatios = gears(gears, numbers)
        assertEquals(78915902, gearRatios.sum())
    }

    private fun readMap(case: AdventCase): Pair<MutableMap<P2, Char>, MutableList<NumberWithPositions>> {
        val symbols = mutableMapOf<P2, Char>()
        val numbers = mutableListOf<NumberWithPositions>()

        fun extracted(
            digitStr: String,
            indexX: Int,
            indexY: Int,
            numbers: MutableList<NumberWithPositions>
        ) {
            val num = digitStr.toLong()
            val positions = ((indexX - digitStr.length)..(indexX - 1)).map { P2(it, indexY) }
            numbers.add(NumberWithPositions(num, positions))
        }

        AdventDay.Day03.from2023By(case, AdventPart.Part1, AdventOrder.Order1) { indexY, s ->
            var digitStr = ""

            s.forEachIndexed { indexX, c ->
                when {
                    c == '.' -> {
                        if (digitStr.isNotEmpty()) {
                            extracted(digitStr, indexX, indexY, numbers)
                        }
                        digitStr = ""
                    }

                    c.isDigit() -> {
                        digitStr = digitStr + c
                    }

                    else -> {
                        //символ
                        symbols[P2(indexX, indexY)] = c
                        if (digitStr.isNotEmpty()) {
                            extracted(digitStr, indexX, indexY, numbers)
                            digitStr = ""
                        }
                    }
                }
            }
            if (digitStr.isNotEmpty()) {
                extracted(digitStr, s.length, indexY, numbers)
                digitStr = ""
            }

        }
        return symbols to numbers
    }

}