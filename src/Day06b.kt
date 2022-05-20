import java.io.File

fun main() {
    val fishCount = LongArray(9) { 0 }

    File("data/day06_input.txt")
        .readText()
        .trim()
        .split(",")
        .map { it.toInt() }
        .forEach {
            fishCount[it]++
        }

    repeat(256) {
        val newCount = fishCount[0]
        for (i in 0..7) {
            fishCount[i] = fishCount[i + 1]
        }

        fishCount[6] += newCount
        fishCount[8] = newCount
    }

    println(fishCount.sum())
}