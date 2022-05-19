import java.io.File

data class Point(val x: Int, val y: Int)

data class Line(val p1: Point, val p2: Point) {
    constructor(x1: Int, y1: Int, x2: Int, y2: Int) : this(Point(x1, y1), Point(x2, y2))

    // all the points the (horizontal, vertical, or 45 degree) line intersects
    val intersectedPoints: List<Point>
        get() {
            val xRange = IntProgression.fromClosedRange(p1.x, p2.x, if (p2.x > p1.x) 1 else -1)
            val yRange = IntProgression.fromClosedRange(p1.y, p2.y, if (p2.y > p1.y) 1 else -1)

            return when {
                p1.x == p2.x -> yRange.map { y -> Point(p1.x, y) }
                p1.y == p2.y -> xRange.map { x -> Point(x, p1.y) }
                else -> xRange.zip(yRange).map { Point(it.first, it.second) }
            }
        }
}

fun main() {
    val pointMap = mutableMapOf<Point, Int>()
    for (line in readLines()) {
        for (point in line.intersectedPoints) {
            pointMap[point] = pointMap.getOrDefault(point, 0) + 1
        }
    }

    println(pointMap.count { it.value > 1 })
}

fun readLines(): List<Line> {
    val lineRegex = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex()

    return File("data/day05_input.txt")
        .readLines()
        .map { line ->
            val (x1, y1, x2, y2) = lineRegex.find(line)!!.destructured

            Line(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt())
        }
}