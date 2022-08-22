import java.io.File

data class Point(val x: Int, val y: Int)

data class FoldInstruction(val axis: Char, val index: Int)

fun main() {
    val input = File("data/day13_input.txt").readLines()

    val dots = readDots(input)
    val instructions = readInstructions(input)

    // Part 1
    val firstInstruction = instructions.first()
    var result = applyFold(dots, firstInstruction)
    println("There are ${result.size} visible dots now")

    // Part 2
    instructions.drop(1).forEach { fold ->
        result = applyFold(result, fold)
    }

    displayDots(result)
}

private fun displayDots(dots: Set<Point>) {
    val xMax = dots.maxOf { it.x }
    val yMax = dots.maxOf { it.y }

    for (y in 0..yMax) {
        for (x in 0..xMax) {
            print(
                if (Point(x, y) in dots) {
                    '#'
                } else {
                    '.'
                }
            )
        }
        println()
    }
}

private fun readDots(input: List<String>): Set<Point> =
    buildSet {
        input
            .takeWhile { line -> line.isNotBlank() }
            .mapTo(this) { line ->
                val (x, y) = line.split(",")
                Point(x.toInt(), y.toInt())
            }
    }

private fun readInstructions(input: List<String>): List<FoldInstruction> {
    val instructionRegex = """fold along ([xy])=(\d+)""".toRegex()

    return input
        .filter { it.startsWith("fold") }
        .map {
            val (axis, index) = instructionRegex.find(it)!!.destructured
            FoldInstruction(axis[0], index.toInt())
        }
}

private fun applyFold(dots: Set<Point>, instruction: FoldInstruction): Set<Point> {
    val transformed = mutableSetOf<Point>()

    when (instruction.axis) {
        'y' -> {
            transformed.addAll(dots.filter { it.y < instruction.index }) // add all above the fold

            // transform and add all below the fold
            dots.filter { it.y > instruction.index }
                .mapTo(transformed) {
                    it.copy(y = 2 * instruction.index - it.y)
                }
        }
        'x' -> { // horizontal fold
            transformed.addAll(dots.filter { it.x < instruction.index }) // add all left of the fold

            // transform and add all right of the fold
            dots.filter { it.x > instruction.index }
                .mapTo(transformed) {
                    it.copy(x = 2 * instruction.index - it.x)
                }
        }
        else -> throw IllegalStateException()
    }

    return transformed
}

