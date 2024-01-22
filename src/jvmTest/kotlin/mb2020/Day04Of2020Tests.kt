package mb2020

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals

val requiredCodeOfFieldsInPassword = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
val ruleOfFieldsInPassword: Map<String, FieldValidationRule> = listOf(
    BirthYear(),
    IssueYear(),
    ExpirationYear(),
    Height(),
    HairColor(),
    EyeColor(),
    PassportID(),
    CountryID()
).map { it.toPair() }.toMap()


sealed class FieldValidationRule(val code: String) {
    abstract fun isValid(value: String): Boolean

    fun toPair(): Pair<String, FieldValidationRule> = this.code to this
}


class BirthYear : FieldValidationRule("byr") {
    override fun isValid(value: String): Boolean {
        return value.toInt() in 1920..2002
    }
}

class IssueYear : FieldValidationRule("iyr") {
    override fun isValid(value: String): Boolean {
        return value.toInt() in 2010..2020
    }
}

class ExpirationYear : FieldValidationRule("eyr") {
    override fun isValid(value: String): Boolean {
        return value.toInt() in 2020..2030
    }
}

class Height : FieldValidationRule("hgt") {
    override fun isValid(value: String): Boolean {
        return when {
            "cm" in value -> value.split("cm").get(0).toInt() in 150..193
            "in" in value -> value.split("in").get(0).toInt() in 59..76
            else -> false
        }
    }
}

class HairColor : FieldValidationRule("hcl") {
    val regex = """#(\d|[a-f])*""".toRegex()
    override fun isValid(value: String): Boolean {
        return value.length == 7 && regex.matches(value)
    }
}

class EyeColor : FieldValidationRule("ecl") {

    override fun isValid(value: String): Boolean {
        return value in EyeColorType.values().map { it.name }
    }
}

class PassportID : FieldValidationRule("pid") {

    val regex = """(\d)*""".toRegex()

    override fun isValid(value: String): Boolean {
        return value.length == 9 && regex.matches(value)
    }
}

class CountryID : FieldValidationRule("cid") {
    override fun isValid(value: String): Boolean {
        return true
    }
}

enum class EyeColorType {
    amb,
    blu,
    brn,
    gry,
    grn,
    hzl,
    oth
}

data class Passport(val fields: Map<String, String>) {

    val isRequired: Boolean
        get() = fields.size >= requiredCodeOfFieldsInPassword.size && requiredCodeOfFieldsInPassword.all {
            fields.containsKey(
                it
            )
        }

    val isValid: Boolean
        get() = isRequired && fields.all { ruleOfFieldsInPassword[it.key]?.isValid(it.value) == true }
}

class Day04Of2020Tests {

    @Test
    fun part1Example() {
        val entries = readEntries(AdventCase.Example)
        assertEquals(2, entries.count { it.isRequired })
    }

    @Test
    fun part1Task() {
        val entries = readEntries(AdventCase.Task)
        assertEquals(213, entries.count { it.isRequired })
    }

    @Test
    fun part2Example() {
        val entries = readEntries(AdventCase.Example)
        assertEquals(2, entries.count { it.isValid })
    }

    @Test
    fun part2ExampleInvalidPasswords() {
        val entries = readEntries(AdventCase.Example, AdventOrder.Order2)
        assertEquals(0, entries.count { it.isValid })
    }

    @Test
    fun part2ExampleValidPasswords() {
        val entries = readEntries(AdventCase.Example, AdventOrder.Order3)
        assertEquals(4, entries.count { it.isValid })
    }

    @Test
    fun part2Task() {
        val entries = readEntries(AdventCase.Task)
        assertEquals(147, entries.count { it.isValid })
    }

    private fun readEntries(case: AdventCase, order: AdventOrder = AdventOrder.Order1): List<Passport> =
        AdventDay.Day04.parts2020By(case, AdventPart.Part1, order, true) { lines ->
            val fields = lines.map {
                it.split(" ").map {
                    val (code, value) = it.split(":")
                    code to value
                }
            }.flatten()
            Passport(fields.toMap())
        }
}