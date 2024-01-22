package mb2020

import mb.parser.*
import kotlin.test.Test
import kotlin.test.assertEquals


data class SeatNumber(val binaryCode: String) {

    val row: Int by lazy { findNumber(0, binaryCode.subSequence(0, 7), 'F', 0, 127) }
    val column: Int by lazy { findNumber(0, binaryCode.subSequence(7, 10), 'L', 0, 7) }
    val seatId: Int by lazy { calcSeatId(row, column) }

    private tailrec fun findNumber(i: Int, subSequence: CharSequence, loverChar: Char, min: Int, max: Int): Int {
        val code = subSequence.get(i)
        if (max - min == 1) {
            return if (code == loverChar) min else max
        } else {
            val newEdge = (max - min) / 2
            val edges = if (code == loverChar) Pair(min, min + newEdge) else Pair(min + newEdge + 1, max)
            return findNumber(i + 1, subSequence, loverChar, edges.first, edges.second)
        }
    }

    companion object {
        fun calcSeatId(row: Int, column: Int) : Int = row * 8 + column
    }


}

class Day05Of2020Tests {

    @Test
    fun part1Example() {
        val seatNumber1 = SeatNumber("FBFBBFFRLR")
        assertEquals(44, seatNumber1.row)
        assertEquals(5, seatNumber1.column)
        assertEquals(357, seatNumber1.seatId)

        val seatNumber2 = SeatNumber("BFFFBBFRRR")
        assertEquals(70, seatNumber2.row)
        assertEquals(7, seatNumber2.column)
        assertEquals(567, seatNumber2.seatId)

        val seatNumber3 = SeatNumber("FFFBBBFRRR")
        assertEquals(14, seatNumber3.row)
        assertEquals(7, seatNumber3.column)
        assertEquals(119, seatNumber3.seatId)

        val seatNumber4 = SeatNumber("BBFFBBFRLL")
        assertEquals(102, seatNumber4.row)
        assertEquals(4, seatNumber4.column)
        assertEquals(820, seatNumber4.seatId)
    }

    @Test
    fun part1Task() {
        val entries = readEntries(AdventCase.Task)
        val seatNumberWithMaxSeatId = entries.maxByOrNull { it.seatId }
        assertEquals(911, seatNumberWithMaxSeatId?.seatId)
    }


    @Test
    fun part2Task() {
        val entries = readEntries(AdventCase.Task)

        val max = entries.maxByOrNull { it.row }
        val min = entries.minByOrNull { it.row }

        assertEquals(12, min?.row)
        assertEquals(113, max?.row)


        val notFullRow = entries.groupBy { it.row }.filter { it.value.size < 8}


        val rowNumbers = notFullRow.values.flatten()
        val fullColumn = (0..7).toMutableList()

        rowNumbers.forEach {
            fullColumn.remove(it.column)
        }
        val myColumn = fullColumn.first()
        val myRow = rowNumbers.first().row


        notFullRow.entries.forEach {
            println("${it.key} ${it.value.size}")
        }

        assertEquals(629, SeatNumber.calcSeatId(myRow, myColumn))


    }

    private fun readEntries(case: AdventCase, order: AdventOrder = AdventOrder.Order1): List<SeatNumber> =
        AdventDay.Day05.from2020By(case, AdventPart.Part1, order) { _, s ->
            SeatNumber(s)
        }
}