import java.io.File

fun main() {
    val input = File("data/day14_input.txt")
        .readLines()

    val polymer = input.first()

    val rules = input.drop(2)
        .takeWhile { it.isNotBlank() }
        .associate {
            val (match, insert) = it.split(" -> ")
            match to insert
        }

    var result = polymer
    repeat(10) {
        result = step(result, rules)
    }

    val counts = result.groupingBy { it }.eachCount()
    val mostCommon = counts.maxOf { it.value }
    val leastCommon = counts.minOf { it.value }

    println(mostCommon - leastCommon)
}

private fun step(polymer: String, rules: Map<String, String>): String = buildString {
    append(polymer.first())

    for (i in 1..polymer.lastIndex) {
        val pair = polymer.substring(i - 1, i + 1)

        if (pair in rules) {
            append(rules[pair])
        }

        append(polymer[i])
    }
}