import kotlin.math.abs

object day7 {

    fun parseInput(input: List<String>): List<Int> = input.first().split(",").map { it.toInt() }

    tailrec fun findLowestFuelCost(positions: List<Int>, current: Int = positions.minOrNull()!!, max: Int = positions.maxOrNull()!!, result: Long = Long.MAX_VALUE, accFunc: (Long, Int, Int) -> Long): Long {
        if (current == max) return result
        val cost = positions.fold(0L) { acc, pos -> accFunc(acc, current, pos) }
        return findLowestFuelCost(positions, current+1, max, if (cost < result) cost else result, accFunc)
    }

    fun pt1(input: List<String>): Long =
        findLowestFuelCost(parseInput(input)) { totalCostAtCurrent, current, pos ->
            totalCostAtCurrent + abs(current - pos)
        }

    fun pt2(input: List<String>): Long =
        findLowestFuelCost(parseInput(input)) { totalCostAtCurrent, current, pos ->
            totalCostAtCurrent + (1 .. abs(current - pos)).fold(0L) { costForPosition, distanceFromCurrent ->
                costForPosition + distanceFromCurrent
            }
        }

}