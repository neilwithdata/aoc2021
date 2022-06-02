import java.io.File

// convenience function to return the key from a map given a (typically) unique value (eg. lazy bimap)
fun <K, V> Map<K, V>.keyForValue(value: V): K = entries.first { it.value == value }.key

fun main() {
    val sum = File("data/day08_input.txt")
        .readLines()
        .sumOf { decodeSignals(it) }

    println("Sum of the output values is $sum")
}

private fun decodeSignals(line: String): Int {
    val patterns = line
        .trim()
        .split(' ', '|')
        .filter { it.isNotBlank() }
        .map {
            it.toCharArray().sorted().joinToString("")
        }

    val input = patterns.take(10)
    val mapping = decodeInputs(input)

    // Convert each output to a digit using the decoded mapping
    val output = patterns.takeLast(4)
    return decodeOutput(output, mapping)
}

private fun decodeInputs(patterns: List<String>): Map<String, Int> {
    val patternMap = mutableMapOf<String, Int>()

    // Working with a sorted list allows us to make just a single pass
    for (pattern in patterns.sortedBy { it.length }) {
        when (pattern.length) {
            2 -> patternMap[pattern] = 1
            3 -> patternMap[pattern] = 7
            4 -> patternMap[pattern] = 4
            7 -> patternMap[pattern] = 8
            5, 6 -> {
                val onePattern = patternMap.keyForValue(1).toSet()
                val fourPattern = patternMap.keyForValue(4).toSet()

                val patternSet = pattern.toSet()

                patternMap[pattern] =
                    if (pattern.length == 5) { // Could be one of 2, 3, or 5
                        when {
                            patternSet.intersect(onePattern).count() == 2 -> 3
                            patternSet.intersect(fourPattern).count() == 2 -> 2
                            else -> 5
                        }
                    } else { // Could be one of 0, 6, 9
                        when {
                            patternSet.intersect(fourPattern).count() == 4 -> 9
                            patternSet.intersect(onePattern).count() == 2 -> 0
                            else -> 6
                        }
                    }
            }
        }
    }

    return patternMap
}

private fun decodeOutput(output: List<String>, mapping: Map<String, Int>): Int =
    output.map { mapping[it] }.joinToString("").toInt()