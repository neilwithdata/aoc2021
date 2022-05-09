import java.io.File

object Submarine {
    var dx = 0
    var dy = 0

    fun processCommand(commandString: String) {
        val parts = commandString.split(" ")

        val command = parts[0]
        val units = parts[1].toInt()

        when (command) {
            "forward" -> dx += units
            "down" -> dy += units
            "up" -> dy -= units
        }
    }
}

fun main() {
    File("data/day02_input.txt")
        .forEachLine { command ->
            Submarine.processCommand(command)
        }

    println(Submarine.dx * Submarine.dy)
}