import java.io.File

private fun MutableMap<String, Long>.increaseCountForKey(key: String, inc: Long) {
    this[key] = this.getOrDefault(key, 0L) + inc
}

fun main() {
    val input = File("data/day14_input.txt")
        .readLines()

    val polymer = input.first()

    var pairs = buildMap {
        for (index in 0 until polymer.lastIndex) {
            val pair = polymer.substring(index, index + 2)
            this.increaseCountForKey(pair, 1)
        }
    }

    val rules = input.drop(2)
        .takeWhile { it.isNotBlank() }
        .associate {
            val (match, insert) = it.split(" -> ")
            match to insert
        }

    val letterCounts = polymer
        .groupingBy { it.toString() }.eachCount()
        .mapValues { it.value.toLong() }
        .toMutableMap()

    repeat(40) {
        val newPairs = mutableMapOf<String, Long>()

        for ((pair, count) in pairs) {
            if (pair in rules.keys) {
                // Two new pairs are created
                val newChar = rules[pair]!!

                val newPair1 = "${pair[0]}${newChar}"
                newPairs.increaseCountForKey(newPair1, count)

                val newPair2 = "${newChar}${pair[1]}"
                newPairs.increaseCountForKey(newPair2, count)

                letterCounts.increaseCountForKey(newChar, count)
            } else {
                newPairs.increaseCountForKey(pair, count)
            }
        }

        pairs = newPairs.toMap()
    }

    val mostCommon = requireNotNull(letterCounts.maxByOrNull { it.value })
    val leastCommon = requireNotNull(letterCounts.minByOrNull { it.value })

    println(mostCommon)
    println(leastCommon)
    println(mostCommon.value - leastCommon.value)
}