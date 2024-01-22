package mb2023

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals


data class Card(val id: Int, val winNumbers: List<Long>, val ownNumbers: List<Long>) {

    fun getOwnWinNumbers(): List<Long> {
        return ownNumbers.filter { winNumbers.contains(it) }
    }
}

fun List<Long>.calcPoints(): Long {
    return when (size) {
        0 -> 0L
        1 -> 1L
        2 -> 2L
        else -> {
            (0..size - 2).fold(1) { acc, v -> acc * 2 }
        }
    }
}

fun findIncCards(cards: Map<Int, Card>, checkCards: List<Card>): Int {
    val sizeLevelCards = checkCards.size
    val totalLevelUp = checkCards.map {
        val sizeNextCardForCheck = it.getOwnWinNumbers().size
        val nextLevelCards = (it.id + 1..it.id + sizeNextCardForCheck).map {
            cards[it]!!
        }
        findIncCards(cards, nextLevelCards)
    }.sum()
    return totalLevelUp + sizeLevelCards
}


class Day04JvmTests {

    @Test
    fun part1Example() {
        val cards = readCards(AdventCase.Example)

        val ownWinNumbers = cards.map {
            it.getOwnWinNumbers()
        }
        val total = ownWinNumbers.map {
            it.calcPoints()
        }.sum()
        assertEquals(13L, total)
    }

    @Test
    fun part1Task() {
        val cards = readCards(AdventCase.Task)
        val ownWinNumbers = cards.map {
            it.getOwnWinNumbers()
        }
        val total = ownWinNumbers.map {
            it.calcPoints()
        }.sum()
        assertEquals(22674L, total)
    }

    @Test
    fun part2Example() {
        val cards = readCards(AdventCase.Example)
        val mapCards = mutableMapOf<Int, Card>()
        cards.forEach {
            mapCards[it.id] = it
        }
        val count = cards.map {
            findIncCards(mapCards, listOf(it))
        }.sum()
        assertEquals(30, count)
    }

    @Test
    fun part2Task() {
        val cards = readCards(AdventCase.Task)
        val mapCards = mutableMapOf<Int, Card>()
        cards.forEach {
            mapCards[it.id] = it
        }
        val count = cards.map {
            findIncCards(mapCards, listOf(it))
        }.sum()
        assertEquals(5747443, count)

    }

    private fun readCards(case: AdventCase): List<Card> =
        AdventDay.Day04.from2023By(case, AdventPart.Part1, AdventOrder.Order1) { _, s ->
            val tmp1 = s.split(":")
            val cardId = tmp1[0].replace("Card ", "").trim().toInt()
            val tmp2 = tmp1[1].split("|")

            val winNumbers = tmp2[0].trim().split(" ").filter { it.isNotBlank() }.map {
                it.trim().toLong()
            }
            val ownNumbers = tmp2[1].trim().split(" ").filter { it.isNotBlank() }.map {
                it.trim().toLong()
            }
            Card(cardId, winNumbers, ownNumbers)
        }


}