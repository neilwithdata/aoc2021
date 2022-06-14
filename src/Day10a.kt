import java.io.File

private val PAIRS = mapOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>'
)

fun main() {
    val result = File("data/day10_input.txt")
        .readLines().sumOf {
            when (scanLine(it)) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> 0L
            }
        }

    println(result)
}

private fun scanLine(line: String): Char? {
    val stack = ArrayDeque<Char>()

    for (c in line) {
        when (c) {
            in PAIRS.keys -> {
                stack.addLast(c)
            }
            in PAIRS.values -> {
                val top = stack.removeLastOrNull() ?: return null

                if (PAIRS[top] != c) {
                    return c
                }
            }
            else -> throw IllegalArgumentException()
        }
    }

    return null
}