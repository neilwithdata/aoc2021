import java.io.File

fun main() {
    val lines = File("data/day03_input.txt")
        .readLines()

    val oxygenRating = findRating(lines, true)
    val scrubberRating = findRating(lines, false)

    println(oxygenRating.toInt(2) * scrubberRating.toInt(2))
}

private fun findRating(lines: List<String>, mostCommon: Boolean): String {
    var filtered = lines
    for (index in lines[0].indices) {
        filtered = filtered.filter(index, mostCommon)
        if (filtered.size == 1)
            break
    }

    return filtered[0]
}

private fun List<String>.filter(index: Int, mostCommon: Boolean): List<String> {
    val onesCount = this.count { it[index] == '1' }
    val zerosCount = this.size - onesCount

    val keep = when {
        onesCount >= zerosCount -> {
            if (mostCommon) '1' else '0'
        }
        else -> {
            if (mostCommon) '0' else '1'
        }
    }

    return this.filter { it[index] == keep }
}