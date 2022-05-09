import java.io.File

fun main() {
    val depths = File("data/day01_input.txt")
        .readLines()
        .map { it.toInt() }

    var increases = 0
    for (i in 1..depths.lastIndex) {
        if (depths[i] > depths[i - 1]) {
            increases++
        }
    }

    println("The depth increases $increases times")
}

