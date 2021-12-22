import kotlinx.collections.immutable.persistentListOf
import kotlin.math.max
import kotlin.math.min

object day22 {

    fun IntRange.fastIntersect(other: IntRange): IntRange? =
        when {
            first > other.last -> null
            last < other.first -> null
            else -> max(first, other.first)..min(last, other.last)
        }

    data class Instruction(val turnOn: Boolean, val cube: Cuboid)

    data class Cuboid(val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {
        val volume: Long by lazy { (xRange.last - xRange.first +1).toLong() * (yRange.last - yRange.first +1) * (zRange.last - zRange.first +1) }
        fun intersection(other: Cuboid): Cuboid? {
            val xIntersect = xRange.fastIntersect(other.xRange)
            val yIntersect = yRange.fastIntersect(other.yRange)
            val zIntersect = zRange.fastIntersect(other.zRange)
            return if (xIntersect != null && yIntersect != null && zIntersect != null)
                Cuboid(xIntersect.first()..xIntersect.last(), yIntersect.first()..yIntersect.last(), zIntersect.first()..zIntersect.last()) else null
        }

        companion object {
            fun fromString(input: String): Cuboid {
                val (x1,x2,y1,y2,z1,z2) = """x=(.+)\.\.(.+),y=(.+)\.\.(.+),z=(.+)\.\.(.+)""".toRegex().find(input)!!.destructured
                return Cuboid(x1.toInt()..x2.toInt(), y1.toInt()..y2.toInt(), z1.toInt()..z2.toInt())
            }
        }
    }

    fun solve(input: List<String>): Long {
        val instructions: List<Instruction> = input.map { Instruction(it.startsWith("on"), Cuboid.fromString(it.substringAfter(" "))) }
        return instructions.fold(persistentListOf<Instruction>()) { acc, instr ->
            val compensations = acc.mapNotNull { s ->
                val intersection = s.cube.intersection(instr.cube)
                if (intersection != null) Instruction(!s.turnOn, intersection) else null
            }
            if (instr.turnOn) acc.add(instr).addAll(compensations) else acc.addAll(compensations)
        }.fold(0L) { acc, s ->
            val c = s.cube.volume
            acc + if (s.turnOn) c else -c
        }
    }

    fun pt1(input: List<String>): Long = solve(input.take(20))

    fun pt2(input: List<String>): Long = solve(input)
}