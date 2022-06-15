import java.io.File

private val PAIRS = mapOf(
    '(' to ')', '[' to ']', '{' to '}', '<' to '>'
)

fun main() {
    val scores = File("data/day10_input.txt").readLines().mapNotNull {
        scanLine(it)?.fold(0L) { acc, c ->
            (acc * 5) + when (c) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> 0
            }
        }
    }.sorted()

    println(scores[scores.size / 2])
}

// return null if corrupted or the completion of the line if incomplete
private fun scanLine(line: String): String? {
    val stack = ArrayDeque<Char>()

    for (c in line) {
        when (c) {
            in PAIRS.keys -> {
                stack.addFirst(c)
            }
            in PAIRS.values -> {
                val top = stack.removeFirstOrNull() ?: return null

                if (PAIRS[top] != c) {
                    return null // invalid line
                }
            }
            else -> throw IllegalArgumentException()
        }
    }

    // line is not corrupted, so must be incomplete (i.e. stack is not empty)
    return stack.joinToString(separator = "") { PAIRS[it].toString() }
}