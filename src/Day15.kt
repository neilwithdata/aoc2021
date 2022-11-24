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

class Cavern(input: List<String>) {
    private val size = input.count()
    private val endLocation = Location(size - 1, size - 1)

    private val grid = Array(size) { row ->
        Array(size) { col ->
            input[row][col].digitToInt()
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

    companion object {
        fun fromFile(filename: String): Cavern {
            val input = File(filename).readLines()
            return Cavern(input)
        }
    }
}

fun main() {
    val cavern = Cavern.fromFile("data/day15_input.txt")
    println(cavern)

    println(cavern.findLowestRiskPath())
}