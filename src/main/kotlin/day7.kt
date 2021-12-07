import kotlin.math.abs

object day7 {

    fun parseInput(input: List<String>): List<Int> = input.first().split(",").map { it.toInt() }

    tailrec fun findLowestFuelCost(positions: List<Int>, current: Int = positions.minOrNull()!!, max: Int = positions.maxOrNull()!!, result: Long = Long.MAX_VALUE, accFunc: (Int, Int) -> Long): Long {
        if (current == max) return result
        val cost = positions.fold(0L) { acc, pos -> acc + accFunc(current, pos) }
        return findLowestFuelCost(positions, current+1, max, if (cost < result) cost else result, accFunc)
    }

    fun pt1(input: List<String>): Long = findLowestFuelCost(parseInput(input)) { current, pos -> abs(current - pos).toLong() }

    fun pt2(input: List<String>): Long = findLowestFuelCost(parseInput(input)) { current, pos ->
            val n = abs(current - pos)
            n*(n+1)/2L // == 1+2+..+n
        }

}