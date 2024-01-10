package mb2022

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals

val lowerAlphabet = "abcdefghijklmnopqrstuvwxyz"
val upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

fun Char.priority(): Int {
    val indexInLower = lowerAlphabet.indexOf(this)
    if (indexInLower >= 0) {
        return indexInLower + 1
    }
    val indexInUpper = upperAlphabet.indexOf(this)
    if (indexInUpper >= 0) {
        return indexInUpper + 27
    }
    throw IllegalArgumentException("cant find index for '${this}'")
}

data class Rucksack(val firstCompartment: String, val secondCompartment: String) {
    fun shareItems(): Set<Char> {
        return firstCompartment.toList().intersect(secondCompartment.toList())
    }

    fun compartments(): String = firstCompartment + secondCompartment
}

data class GroupRucksack(val rucksacks: List<Rucksack>) {
    fun badge(): Char {
        var tempBadges = rucksacks.first().compartments().toSet()
        rucksacks.forEach { rucksack ->
            tempBadges = tempBadges.intersect(rucksack.compartments().toList())
        }
        if (tempBadges.size != 1) throw IllegalStateException("wrong number badges ${tempBadges}")
        return tempBadges.single()
    }
}

class Day03Of2022Tests {
    @Test
    fun example1() {
        val rucksacks = readRucksacks(AdventCase.Example)
        assertEquals(6, rucksacks.size)
        assertEquals("vJrwpWtwJgWr", rucksacks.first().firstCompartment)
        assertEquals("hcsFMMfFFhFp", rucksacks.first().secondCompartment)

        assertEquals(setOf('p'), rucksacks.first().shareItems())
        assertEquals(16, rucksacks.first().shareItems().first().priority())
        assertEquals(157, rucksacks.sumOf { it.shareItems().map { it.priority() }.sum() })
    }

    @Test
    fun task1() {
        val rucksacks = readRucksacks(AdventCase.Task)
        assertEquals(8153, rucksacks.sumOf { it.shareItems().map { it.priority() }.sum() })
    }

    @Test
    fun handShapeScopesExample2() {
        val rucksacks = readRucksacks(AdventCase.Example)
        val groups = rucksacks.chunked(3).map { GroupRucksack(it) }
        assertEquals(70, groups.sumOf { it.badge().priority() })
    }

    @Test
    fun handShapeScopes2() {
        val rucksacks = readRucksacks(AdventCase.Task)
        val groups = rucksacks.chunked(3).map { GroupRucksack(it) }
        assertEquals(2342, groups.sumOf { it.badge().priority() })

    }

    private fun readRucksacks(case: AdventCase): List<Rucksack> {
        var rucksacks = AdventDay.Day03.from2022By(case, AdventPart.Part1, AdventOrder.Order1) { _, s ->
            Rucksack(s.substring(0, s.length / 2), s.substring(s.length / 2))
        }
        return rucksacks
    }
}