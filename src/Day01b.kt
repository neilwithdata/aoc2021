import java.io.File

fun main() {
    val depths = File("data/day01_input.txt")
        .readLines()
        .map { it.toInt() }

    val windows = depths
        .mapIndexedNotNull { index, _ ->
            if (index + 2 in depths.indices)
                depths[index] + depths[index + 1] + depths[index + 2]
            else
                null
        }

    var increases = 0
    for (i in 1..windows.lastIndex) {
        if (windows[i] > windows[i - 1]) {
            increases++
        }
    }

    println("The depth increases $increases times")
}