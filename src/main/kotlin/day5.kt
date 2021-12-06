import kotlinx.collections.immutable.persistentHashMapOf
import kotlin.math.max
import kotlin.math.min

object day5 {

    data class Vector(val start: Pos, val end: Pos) {
        val nodes: List<String> by lazy {
            val steps = max(max(start.x, end.x) - min(start.x, end.x), max(start.y, end.y) - min(start.y, end.y))
            val xSteps = if (start.x == end.x) (0 .. steps).map { start.x } else IntProgression.fromClosedRange(start.x, end.x, if (start.x < end.x) 1 else -1)
            val ySteps = if (start.y == end.y) (0 .. steps).map { start.y } else IntProgression.fromClosedRange(start.y, end.y, if (start.y < end.y) 1 else -1)
            xSteps.zip(ySteps).map { "${it.first},${it.second}" }
        }
        fun isStraight() = (start.x == end.x || start.y == end.y)
    }

    fun parseLines(input: List<String>): List<Vector> = input.map { line ->
        val (x1,y1,x2,y2) = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex().find(line)!!.destructured
        Vector(Pos(x1.toInt(), y1.toInt()), Pos(x2.toInt(), y2.toInt()))
    }

    fun List<Vector>.countIntersections(): Int = flatMap { it.nodes }
        .fold(persistentHashMapOf<String, Int>()) { freqMap, node -> freqMap.put(node, freqMap.getOrDefault(node, 0)+1) }
        .values.count { it > 1 }

    fun pt1(input: List<String>): Int = parseLines(input).filter { it.isStraight() }.countIntersections()

    fun pt2(input: List<String>): Int = parseLines(input).countIntersections()

}