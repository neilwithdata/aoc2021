import java.io.File

fun main() {
    val lines = File("data/day03_input.txt")
        .readLines()

    val sums = IntArray(lines[0].length) { 0 }
    for (line in lines) {
        for ((i, c) in line.withIndex()) {
            sums[i] += c.digitToInt()
        }
    }

    var gamma = ""
    var epsilon = ""
    val threshold = (lines.size / 2) + 1
    for (sum in sums) {
        if (sum >= threshold) {
            gamma += "1"
            epsilon += "0"
        } else {
            gamma += "0"
            epsilon += "1"
        }
    }

    println("Power consumption: ${gamma.toInt(2) * epsilon.toInt(2)}")
}