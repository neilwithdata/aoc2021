import java.io.BufferedReader
import java.io.File

class Board(lines: List<String>) {
    data class Entry(var value: Int, var marked: Boolean = false)

    private val entries = Array(BOARD_SIZE) {
        Array(BOARD_SIZE) {
            Entry(0)
        }
    }

    init {
        val regex = """\d+""".toRegex()

        for ((row, line) in lines.withIndex()) {
            regex.findAll(line)
                .map { it.value.toInt() }
                .forEachIndexed { col, it ->
                    entries[row][col].value = it
                }
        }
    }

    companion object {
        const val BOARD_SIZE = 5
    }

    fun markNumber(n: Int) {
        for (entry in entries.flatten()) {
            if (entry.value == n) {
                entry.marked = true
            }
        }
    }

    fun didWin(): Boolean {
        // check each row
        for (row in entries) {
            if (row.all { it.marked })
                return true
        }

        // check each column
        for (col in 0 until BOARD_SIZE) {
            if (entries.map { it[col] }.all { it.marked })
                return true
        }

        return false
    }

    fun calculateBoardScore(finalNumber: Int): Int =
        finalNumber * entries
            .flatten()
            .filter { !it.marked }
            .sumOf { it.value }
}


fun main() {
    val reader = File("data/day04_input.txt").bufferedReader()
    val nums = reader.readLine().split(",").map { it.toInt() }
    val boards = mutableListOf<Board>()

    while (true) {
        val boardInput = readNextBoard(reader) ?: break
        boards.add(Board(boardInput))
    }

    for (n in nums) {
        for (board in boards) {
            board.markNumber(n)

            if (board.didWin()) {
                println("Winning score is ${board.calculateBoardScore(n)}")
                return
            }
        }
    }
}

private fun readNextBoard(reader: BufferedReader): List<String>? {
    val board = mutableListOf<String>()

    while (board.size < Board.BOARD_SIZE) {
        val line = reader.readLine() ?: break

        if (line.isEmpty())
            continue

        board.add(line)
    }

    return board.takeIf { it.size == 5 }
}
