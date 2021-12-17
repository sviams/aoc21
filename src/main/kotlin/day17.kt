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

    tailrec fun hitsForX(target: TargetBox, dx: Int, dy: Int = target.yRange.first, result: PersistentList<Pos> = persistentListOf()): PersistentList<Pos> {
        return if (dy > abs(target.yRange.first)) result
        else hitsForX(target, dx, dy + 1, if (target.contains(calculateTrajectory(dx, dy, target.yRange.first))) result.add(Pos(dx, dy)) else result)
    }

    tailrec fun trajectoriesThatHit(target: TargetBox, dx: Int = 0, result: PersistentList<Pos> = persistentListOf()): PersistentList<Pos> {
        return if (dx > target.xRange.last) result else trajectoriesThatHit(target, dx + 1, result.addAll(hitsForX(target, dx)))
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