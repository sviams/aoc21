import kotlinx.collections.immutable.persistentHashMapOf
import kotlin.math.max
import kotlin.math.min

object day5 {

    data class Vector(val start: Pos, val end: Pos) {
        val nodes: List<String> by lazy {
            val steps = max(max(start.x, end.x) - min(start.x, end.x), max(start.y, end.y) - min(start.y, end.y))
            val xSteps = if (start.x == end.x) (0 .. steps).map { start.x } else if (start.x > end.x) (start.x downTo end.x).toList() else (start.x .. end.x).toList()
            val ySteps = if (start.y == end.y) (0 .. steps).map { start.y } else if (start.y > end.y) (start.y downTo end.y).toList() else (start.y .. end.y).toList()
            xSteps.zip(ySteps).map { "${it.first},${it.second}" }
        }
        fun isVertical() = (start.x == end.x || start.y == end.y)
    }

    fun parseLines(input: List<String>): List<Vector> = input.map { line ->
        val (x1,y1,x2,y2) = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex().find(line)!!.destructured
        Vector(Pos(x1.toInt(), y1.toInt()), Pos(x2.toInt(), y2.toInt()))
    }

    fun pt1(input: List<String>): Int =
        parseLines(input).filter { it.isVertical() }.flatMap { it.nodes }.fold(persistentHashMapOf<String, Int>()) { freqMap, node ->
            val existingValue = freqMap.getOrDefault(node, 0)
            freqMap.put(node, existingValue+1)
        }.values.count { it > 1 }

    fun pt2(input: List<String>): Int =
        parseLines(input).flatMap { it.nodes }.fold(persistentHashMapOf<String, Int>()) { freqMap, node ->
            val existingValue = freqMap.getOrDefault(node, 0)
            freqMap.put(node, existingValue+1)
        }.values.count { it > 1 }
}