import kotlinx.collections.immutable.*
import java.lang.IllegalArgumentException
import java.lang.Math.abs

data class Pos(val x: Int, val y: Int) {
    fun plus(other: Pos) = Pos(x + other.x, y + other.y)
    fun minus(other: Pos) = Pos(x - other.x, y - other.y)
    fun times(other: Pos) = Pos(x * other.x, y * other.y)
    fun distanceTo(other: Pos) = abs(x - other.x) + abs(y - other.y)
    fun product() = x * y
    fun neighbors() = hashSetOf(this.plus(NORTH), this.plus(SOUTH), this.plus(EAST), this.plus(WEST))
    override fun equals(other: Any?): Boolean {
        val o = other as Pos
        return x == o.x && y == o.y
    }

    fun isInFirstQuadrant(): Boolean = x >= 0 && y >= 0

    companion object {
        // Assuming TL is origin
        val NORTH = Pos(0,-1)
        val SOUTH = Pos(0,1)
        val WEST = Pos(-1,0)
        val EAST = Pos(1, 0)
    }
}

fun <T> Sequence<T>.takeWhileInclusive(pred: (T) -> Boolean): Sequence<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = pred(it)
        result
    }
}

typealias Path = PersistentList<Pos>

data class Quad<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
) {
    override fun toString(): String = "($first, $second, $third, $fourth)"

}

object AStarWeighted {

    private const val MAX_SCORE = 99999999

    private tailrec fun generatePath(currentPos: Pos, cameFrom: PersistentMap<Pos, Pos>, result: Path = persistentListOf()): Path {
        val nextResult = result.add(0, currentPos)
        if (!cameFrom.containsKey(currentPos)) return nextResult
        val nextCurrent = cameFrom.getValue(currentPos)
        return generatePath(nextCurrent, cameFrom, nextResult)
    }

    private tailrec fun checkNeighbors(neighbors: PersistentList<Pos>, current: Pos, end: Pos, o: PersistentSet<Pos>, cfs: PersistentMap<Pos, Int>, cf: PersistentMap<Pos, Pos>, ectf: PersistentMap<Pos, Int>, scoreMap: PersistentMap<Pos, Int>)
            : Quad<PersistentSet<Pos>, PersistentMap<Pos, Int>, PersistentMap<Pos, Pos>, PersistentMap<Pos, Int>> {
        if (neighbors.isEmpty()) return Quad(o, cfs, cf, ectf)
        val neighbor = neighbors[0]
        val score = cfs.getValue(current) + scoreMap[current]!!
        val isBetter = score < cfs.getOrDefault(neighbor, MAX_SCORE)
        val no = if (isBetter && !o.contains(neighbor)) o.add(neighbor) else o
        val ncf = if (isBetter) cf.put(neighbor, current) else cf
        val ncfs = if (isBetter) cfs.put(neighbor, score) else cfs
        val nectf = if (isBetter) ectf.put(neighbor, score + neighbor.distanceTo(end)) else ectf
        return checkNeighbors(neighbors.removeAt(0), current, end, no, ncfs, ncf, nectf, scoreMap)
    }

    private tailrec fun walk(
        end: Pos,
        open: PersistentSet<Pos>,
        closed: PersistentSet<Pos>,
        costFromStart: PersistentMap<Pos, Int>,
        estimatedCostToFinish: PersistentMap<Pos, Int>,
        cameFrom: PersistentMap<Pos, Pos>,
        scoreMap: PersistentMap<Pos, Int>
    )
            : Path {
        if (open.isEmpty()) throw IllegalArgumentException("No path to $end")
        val current = open.minByOrNull { estimatedCostToFinish.getValue(it) }!!
        if (current == end) return generatePath(end, cameFrom)
        val openWithoutCurrent: PersistentSet<Pos> = open.remove(current)
        val closedWithCurrent = closed.add(current)
        val openNeighbors = current.neighbors().filter { it.isInFirstQuadrant() }.filterNot { closedWithCurrent.contains(it) }.toPersistentList()
        val (nextOpen, nextCostFromStart, nextCameFrom, nextEstimatedCostToFinish) = checkNeighbors(openNeighbors, current, end, openWithoutCurrent, costFromStart, cameFrom, estimatedCostToFinish, scoreMap)
        return walk(end, nextOpen, closedWithCurrent, nextCostFromStart, nextEstimatedCostToFinish, nextCameFrom, scoreMap)
    }

    fun shortestPath(from: Pos, to: Pos, barriers: ImmutableSet<Pos>, scoreMap: PersistentMap<Pos, Int>): Path =
        walk(to, persistentHashSetOf(from), barriers.toPersistentHashSet(), persistentHashMapOf(from to 0), persistentHashMapOf(from to from.distanceTo(to)), persistentHashMapOf(), scoreMap)
}
