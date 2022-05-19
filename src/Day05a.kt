import java.io.File
import kotlin.math.max
import kotlin.math.min

data class Point(val x: Int, val y: Int)

data class Line(val p1: Point, val p2: Point) {
    constructor(x1: Int, y1: Int, x2: Int, y2: Int) : this(Point(x1, y1), Point(x2, y2))

    // all the points the (horizontal or vertical) line intersects
    val intersectedPoints: List<Point>
        get() = if (p1.x == p2.x)
            (min(p1.y, p2.y)..max(p1.y, p2.y)).map { y -> Point(p1.x, y) }
        else
            (min(p1.x, p2.x)..max(p1.x, p2.x)).map { x -> Point(x, p1.y) }
}

fun main() {
    val lines = readLines()
        .filter { line ->
            (line.p1.x == line.p2.x) || (line.p1.y == line.p2.y)
        }

    val pointMap = mutableMapOf<Point, Int>()
    for (line in lines) {
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