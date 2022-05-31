import java.io.File
import kotlin.math.abs

fun main() {
    val positions = File("data/day07_input.txt")
        .readText()
        .trim()
        .split(",")
        .map { it.toInt() }
        .sorted()

    val min = positions.first()
    val max = positions.last()

    val distanceCosts = IntArray(max - min + 1) { n -> n * (n + 1) / 2 }

    var prevFuelCost: Int = Int.MAX_VALUE
    for (pos in min..max) {
        val fuelCost = positions.sumOf { distanceCosts[abs(pos - it)] }

        if (fuelCost > prevFuelCost) {
            println("optimum found at $prevFuelCost")
            return
        }

        prevFuelCost = fuelCost
    }
}