package mb2021

import mb.parser.*
import mb2023.P2
import kotlin.test.Test
import kotlin.test.assertEquals

data class BingoCard(
    val map: Map<Int, P2>
) {

    fun checkAndUpdate(value: Int): Pair<Boolean, Boolean> {
        val point = map[value]
        if (point == null) {
            return false to false
        } else {
            val row = rowMap[point.y] ?: throw IllegalStateException("cant find row ${point.y}")
            val column = columnMap[point.x] ?: throw IllegalStateException("cant find column ${point.x}")
            row[point.x] = true
            column[point.y] = true
            return true to (row.all { it } || column.all { it })
        }
    }

    fun unmarkedNumbers() : List<Int> {
        val pairs = map.filter { it -> //(number, point)
            rowMap.get(it.value.y)?.get(it.value.x) == false
        }
        return pairs.keys.toList()
    }


    val rowMap: MutableMap<Int, BooleanArray> = mutableMapOf<Int, BooleanArray>().apply {
        (0..4).forEach {
            put(it, BooleanArray(5) { false })
        }
    }
    val columnMap: MutableMap<Int, BooleanArray> = mutableMapOf<Int, BooleanArray>().apply {
        (0..4).forEach {
            put(it, BooleanArray(5) { false })
        }
    }


}

fun playBingo(orders: List<Int>, cards: List<BingoCard>): Pair<Int, BingoCard> {
    for (i in orders.indices) {
        val value = orders[i]
        cards.forEach { card ->
            val (inserted, won) = card.checkAndUpdate(value)
            if (won) {
                return value to card
            }
        }
    }
    throw IllegalStateException("no one won")
}

fun playBingoForEndCards(orders: List<Int>, cards: List<BingoCard>):  List<Pair<Int, BingoCard>> {
    val result = mutableListOf<Pair<Int, BingoCard>>()

    var cardsInGame = mutableListOf<BingoCard>().apply {
        addAll(cards)
    }


    var index = 0

    while (index < orders.size || cardsInGame.isNotEmpty()) {
        val value = orders[index]


        val cardsIterator = cardsInGame.iterator()
        while (cardsIterator.hasNext()) {
            val card = cardsIterator.next()
            val (inserted, won) = card.checkAndUpdate(value)
            if (won) {
                cardsIterator.remove()
                result.add(value to card)
            }
        }
        index++
    }
    return result
}


class Day04Of2021Tests {

    @Test
    fun part1Example() {
        val (orders, cards) = readEntries(AdventCase.Example)
        val (winNumber, wonCard) = playBingo(orders, cards)

        assertEquals(24, winNumber)
        assertEquals(188, wonCard.unmarkedNumbers().sum())
        assertEquals(4512 , winNumber * wonCard.unmarkedNumbers().sum())
    }


    @Test
    fun part1Task() {
        val (orders, cards) = readEntries(AdventCase.Task)
        val (winNumber, wonCard) = playBingo(orders, cards)
        assertEquals(58374 , winNumber * wonCard.unmarkedNumbers().sum())
    }

    @Test
    fun part2Example() {
        val (orders, cards) = readEntries(AdventCase.Example)
        val winners = playBingoForEndCards(orders, cards)
        val (lastNumber, lastCard) = winners.last()
        assertEquals(1924 , lastNumber * lastCard.unmarkedNumbers().sum())
    }

    @Test
    fun part2Task() {
        val (orders, cards) = readEntries(AdventCase.Task)
        val winners = playBingoForEndCards(orders, cards)
        val (lastNumber, lastCard) = winners.last()
        assertEquals(11377 , lastNumber * lastCard.unmarkedNumbers().sum())
    }

    private fun readEntries(case: AdventCase): Pair<List<Int>, List<BingoCard>> {
        val orderOfNumbers = mutableListOf<Int>()
        val cards = AdventDay.Day04.parts2021By(case, AdventPart.Part1, AdventOrder.Order1) { lines ->
            if (lines.size == 1) {
                lines.first().split(",").forEach {
                    orderOfNumbers.add(it.toInt())
                }
                null
            } else {
                val m = mutableMapOf<Int, P2>()

                lines.forEachIndexed { y, s ->
                    s.split(" ").mapNotNull { it.takeIf { it.isNotBlank() }?.toInt() }.forEachIndexed { x, value ->
                        m[value] = P2(x, y)
                    }
                }
                BingoCard(m)
            }
        }.mapNotNull { it }
        return orderOfNumbers to cards
    }
}