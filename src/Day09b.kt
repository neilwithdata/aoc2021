import java.io.File

private data class Location(val row: Int, val col: Int) {
    val adjacentLocations
        get() = listOf(
            Location(row - 1, col),
            Location(row + 1, col),
            Location(row, col - 1),
            Location(row, col + 1)
        )
}

class Heightmap(input: List<String>) {
    private val rows = input.size
    private val cols = input[0].length

    private val data = Array(rows) { row ->
        IntArray(cols) { col ->
            input[row][col].digitToInt()
        }
    }

    operator fun get(location: Location): Int? = this[location.row, location.col]

    operator fun get(row: Int, col: Int): Int? {
        return data.getOrNull(row)?.getOrNull(col)
    }

    private fun findLowPoints(): List<Location> {
        val lowPoints = mutableListOf<Location>()

        // must be lower than all adjacent locations
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val location = Location(row, col)
                val height = this[location]!!

                val adjacent = location.adjacentLocations.mapNotNull { this[it] }

                if (adjacent.all { it > height }) {
                    lowPoints += Location(row, col)
                }
            }
        }

        return lowPoints
    }

    fun calculateBasinSizes(): List<Int> {
        // each low point defines a basin
        val lowPoints = findLowPoints()
        val basinSizes = mutableListOf<Int>()

        for (lowPoint in lowPoints) {
            val basinPoints = mutableListOf<Location>()
            val newPoints = mutableListOf(lowPoint)

            while (newPoints.isNotEmpty()) {
                // pop off the new point and add it to the basin
                val location = newPoints.removeFirst()
                basinPoints.add(location)

                // consider all valid adjacent points not 9, and not already in basin
                newPoints += location.adjacentLocations
                    .filter {
                        (it.row in 0 until rows) && (it.col in 0 until cols)
                                && this[it] != 9
                                && it !in newPoints
                                && it !in basinPoints
                    }
            }

            basinSizes += basinPoints.size
        }

        return basinSizes
    }
}

fun main() {
    val input = File("data/day09_input.txt")
        .readLines()

    val heightmap = Heightmap(input)
    val result = heightmap.calculateBasinSizes()
        .sortedDescending()
        .take(3)
        .reduce { acc, i -> acc * i }

    println(result)
}