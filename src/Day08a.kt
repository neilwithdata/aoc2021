import java.io.File

fun main() {
    val count = File("data/day08_input.txt")
        .readLines()
        .sumOf { line ->
            val outputDigits = line.substringAfter('|').trim().split(' ')
            outputDigits.count { digit -> digit.length in listOf(2, 3, 4, 7) }
        }

    println(count)
}