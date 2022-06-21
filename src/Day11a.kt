import java.io.File

data class Location(val row: Int, val col: Int) {
    fun getAdjacentLocations(): List<Location> {
        val locations = mutableListOf<Location>()

        for (dx in -1..1) {
            for (dy in -1..1) {
                locations += Location(row + dy, col + dx)
            }
        }

        locations.remove(this)
        return locations
    }
}

class Grid(input: List<String>) {
    private val rows = input.size
    private val cols = input[0].length

    var totalFlashCount = 0

    private val energyLevels = Array(rows) { row ->
        IntArray(cols) { col ->
            input[row][col].digitToInt()
        }
    }

    private val allLocations: List<Location> =
        (0 until rows).flatMap { row ->
            (0 until cols).map { col ->
                Location(row, col)
            }
        }

    private operator fun get(location: Location): Int = energyLevels[location.row][location.col]

    private operator fun set(location: Location, value: Int) {
        energyLevels[location.row][location.col] = value
    }

    fun step() {
        increaseEnergyLevels(allLocations)
        val flashed = flash()
        resetEnergyLevels(flashed)

        totalFlashCount += flashed.size
    }

    private fun flash(): List<Location> {
        val flashed = mutableListOf<Location>()

        while (true) {
            // Find all greater than 9 that haven't flashed and flash them
            val toFlash = allLocations.filter { location ->
                this[location] > 9 && location !in flashed
            }

            if (toFlash.isNotEmpty()) {
                toFlash.forEach { location ->
                    flashLocation(location)
                    flashed += location
                }
            } else {
                break
            }
        }

        return flashed
    }

    private fun flashLocation(location: Location) {
        // Increase energy level for all valid adjacent locations
        increaseEnergyLevels(
            location.getAdjacentLocations().filter {
                it.row in 0 until rows && it.col in 0 until cols
            }
        )
    }

    private fun increaseEnergyLevels(locations: List<Location>) {
        for (location in locations) {
            this[location]++
        }
    }

    private fun resetEnergyLevels(locations: List<Location>) {
        for (location in locations) {
            this[location] = 0
        }
    }

    fun display() {
        for (row in energyLevels) {
            println(row.joinToString(separator = ""))
        }
        println()
    }
}

fun main() {
    val input = File("data/day11_input.txt").readLines()

    val grid = Grid(input)
    grid.display()

    repeat(100) {
        println("After step ${it + 1}")
        grid.step()
        grid.display()
    }

    println(grid.totalFlashCount)
}