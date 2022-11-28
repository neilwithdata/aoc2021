import java.io.File
import java.util.*

data class Location(val row: Int, val col: Int) {
    val neighbours
        get() = listOf(
            Location(row - 1, col),
            Location(row + 1, col),
            Location(row, col - 1),
            Location(row, col + 1)
        )
}

class Cavern(input: List<String>, sizeFactor: Int = 1) {
    private val originalSize = input.count()

    private val size = originalSize * sizeFactor
    private val endLocation = Location(size - 1, size - 1)

    private val grid = Array(size) { row ->
        Array(size) { col ->
            if (row < originalSize && col < originalSize) {
                input[row][col].digitToInt()
            } else {
                0
            }
        }
    }

    init {
        for (row in 0 until sizeFactor) {
            for (col in 0 until sizeFactor) {
                initGridBlock(row, col)
            }
        }
    }

    private fun initGridBlock(row: Int, col: Int) {
        if (row == 0 && col == 0)
            return // already initialized

        val fromOrigin = if (col == 0) {
            Location((row - 1) * originalSize, 0) // above
        } else {
            Location(row * originalSize, (col - 1) * originalSize) // to the left
        }

        val toOrigin = Location(row * originalSize, col * originalSize)

        // Now copy all the values - incrementing and wrapping if necessary
        for (dx in 0 until originalSize) {
            for (dy in 0 until originalSize) {
                var next = grid[fromOrigin.row + dy][fromOrigin.col + dx] + 1
                if (next == 10) {
                    next = 1
                }

                grid[toOrigin.row + dy][toOrigin.col + dx] = next
            }
        }

    }

    // A* path search
    fun findLowestRiskPath(): Int {
        val costs = mutableMapOf(
            Location(0, 0) to 0
        )

        val frontier = PriorityQueue<Location>(compareBy { location ->
            // Priority = cost + manhattan distance to end
            val steps = (endLocation.col - location.col) + (endLocation.row - location.row)
            costs.getOrDefault(location, 0) + steps
        })

        frontier.add(Location(0, 0))

        while (frontier.isNotEmpty()) {
            val loc = frontier.poll()

            if (loc == endLocation) {
                return costs[loc]!!
            }

            for (neighbour in getNeighbours(loc)) {
                val cost = costs[loc]!! + grid[neighbour.row][neighbour.col]
                if (neighbour !in costs || cost < costs[neighbour]!!) {
                    // we've found a new shorter way to reach this neighbour
                    costs[neighbour] = cost
                    frontier.add(neighbour)
                }
            }
        }

        throw IllegalStateException("No path from start to finish")
    }

    private fun getNeighbours(location: Location): List<Location> =
        location.neighbours
            .filter {
                it.row in (0 until size) && it.col in (0 until size)
            }

    override fun toString(): String =
        buildString {
            for (row in grid) {
                for (col in row) {
                    append(col)
                }
                appendLine()
            }
        }
}

fun main() {
    val input = File("data/day15_input.txt")
        .readLines()

    // Part 1
    val cavern = Cavern(input, 1)
    println(cavern.findLowestRiskPath())

    // Part 2
    val largerCavern = Cavern(input, 5)
    println(largerCavern.findLowestRiskPath())
}