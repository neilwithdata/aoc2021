import java.io.File
import kotlin.math.abs

fun main() {
    val positions = File("data/day07_input.txt")
        .readText()
        .trim()
        .split(",")
        .map { it.toInt() }
        .sorted()

    val median = if (positions.size % 2 == 0) {
        positions[positions.size / 2 - 1]
    } else {
        positions[(positions.size + 1) / 2 - 1]
    }

    println(positions.sumOf {
        abs(it - median)
    })
}
