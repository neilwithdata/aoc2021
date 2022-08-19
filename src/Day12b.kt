import java.io.File

data class Cave(val name: String) {
    val isBig: Boolean = name[0].isUpperCase()
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
                // progress this path along all valid connections
                val visitedSmallCaves = path.filter { !it.isBig }
                val adjacent = adjacencies[currentCave]!!

                // have we visited any small caves more than once
                val visitedSmallCaveMoreThanOnce = visitedSmallCaves.groupBy { it.name }.any { it.value.size > 1 }

                /**
                Conditions for possible next steps are:
                1/ cave must not be the start cave; and
                2/ cave is big; or
                3/ cave is small, not visited before; or
                4/ cave is small, visited before, but we haven't visited any small caves more than once
                 */

                val possibles = adjacent.filter { cave ->
                    cave != startCave &&
                            (cave.isBig ||
                                    cave !in visitedSmallCaves ||
                                    (cave in visitedSmallCaves && !visitedSmallCaveMoreThanOnce))
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