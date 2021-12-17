import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlin.math.abs

object day17 {

    data class TargetBox(val xRange: IntRange, val yRange: IntRange) {
        fun contains(trajectory: List<Pos>): Boolean = trajectory.any { it.x in xRange && it.y in yRange }
    }

    fun parseInput(input: String): TargetBox {
        val (x1, x2, y1, y2) = """.+x=(\S+)\.\.(\S+), y=(\S+)\.\.(\S+)""".toRegex().find(input)!!.destructured
        return TargetBox(IntRange(x1.toInt(),x2.toInt()), y1.toInt() .. y2.toInt())
    }

    tailrec fun calculateTrajectory(dx: Int, dy: Int, bottom: Int, result: PersistentList<Pos> = persistentListOf()): List<Pos> {
        val last = result.lastOrNull() ?: Pos(0,0)
        return if (last.y < bottom) result.dropLast(1)
        else calculateTrajectory(if (dx == 0) 0 else dx - 1, dy-1, bottom, result.add(Pos(last.x + dx, last.y + dy)))
    }

    fun trajectoriesThatHit(target: TargetBox): PersistentList<Pos> =
        (0 .. target.xRange.last).fold(persistentListOf()) { hits, dx ->
            hits.addAll((target.yRange.first .. abs(target.yRange.first)).fold(persistentListOf()) { acc, dy ->
                if (target.contains(calculateTrajectory(dx, dy, target.yRange.first))) acc.add(Pos(dx, dy)) else acc
            })
        }

    fun pt1(input: String): Int {
        val target = parseInput(input)
        val hits = trajectoriesThatHit(target)
        val maxY: Int = hits.maxOf { it.y }
        val best = hits.filter { it.y == maxY }.first()
        return calculateTrajectory(best.x, best.y, target.yRange.first).maxOf { it.y }
    }
    
    fun pt2(input: String): Int = trajectoriesThatHit(parseInput(input)).size
}