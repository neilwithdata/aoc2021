import java.io.File

class Heightmap(input: List<String>) {
    private val data = Array(input.size) { row ->
        IntArray(input[0].length) { col ->
            input[row][col].digitToInt()
        }
    }

    operator fun get(row: Int, col: Int): Int? {
        return data.getOrNull(row)?.getOrNull(col)
    }

    fun findLowPoints(): List<Int> {
        val lowPoints = mutableListOf<Int>()

        // must be lower than all adjacent locations
        for (row in data.indices) {
            for (col in data[0].indices) {
                val height = data[row][col]

                val adjacent = listOf(
                    this[row - 1, col] ?: Int.MAX_VALUE,
                    this[row + 1, col] ?: Int.MAX_VALUE,
                    this[row, col - 1] ?: Int.MAX_VALUE,
                    this[row, col + 1] ?: Int.MAX_VALUE,
                )

                if (adjacent.all { it > height }) {
                    lowPoints += height
                }
            }
        }

        return lowPoints
    }
}

fun main() {
    val input = File("data/day09_input.txt")
        .readLines()

    val heightmap = Heightmap(input)
    val riskSum = heightmap.findLowPoints()
        .sumOf { it + 1 }

    println(riskSum)
}