import java.io.File

data class Cave(val name: String) {
    val isBig: Boolean
        get() = name[0].isUpperCase()
}

object CaveSystem {
    private val adjacencies = LinkedHashMap<Cave, MutableList<Cave>>()

    fun addConnection(first: Cave, second: Cave) {
        adjacencies.getOrPut(first) { mutableListOf() }.add(second)
        adjacencies.getOrPut(second) { mutableListOf() }.add(first)
    }

    fun countPaths(): Int {
        val paths = mutableListOf<List<Cave>>()
        var pathCount = 0

        val startCave = Cave("start")
        val endCave = Cave("end")

        paths.add(listOf(startCave))
        while (paths.isNotEmpty()) {
            val path = paths.removeFirst()
            val currentCave = path.last()

            if (currentCave == endCave) {
                pathCount++
            } else {
                // progress this path along all valid connections (big caves, not visited small caves, and not 'start')
                val visitedSmallCaves = path.filter { !it.isBig }
                val adjacent = adjacencies[currentCave]!!

                val possibles = adjacent.filter { cave ->
                    cave != startCave && (cave.isBig || cave !in visitedSmallCaves)
                }

                possibles.forEach { cave ->
                    val newPath = path.toMutableList().apply { add(cave) }
                    paths.add(newPath)
                }
            }
        }

        return pathCount
    }
}

fun main() {
    File("data/day12_input.txt")
        .forEachLine { line ->
            val (a, b) = line.split("-")
            CaveSystem.addConnection(Cave(a), Cave(b))
        }

    println("Found ${CaveSystem.countPaths()} paths")
}