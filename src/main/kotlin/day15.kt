import kotlinx.collections.immutable.*

object day15 {

    fun parseInput(input: List<String>): Map<Pos, Int> =
        input.foldIndexed(emptyMap()) { row, acc, line ->
            acc + line.foldIndexed(emptyMap()) { col, lineAcc, risk ->
                lineAcc.plus(Pos(col, row) to risk.digitToInt())
            }
        }

    fun findCheapestPath(map: Map<Pos, Int>): Int {
        val w = map.keys.maxByOrNull { it.x }!!.x
        val h = map.keys.maxByOrNull { it.y }!!.y
        val target = Pos(w,h)
        val vertBarriers = (-1 .. h+1).flatMap { listOf(Pos(-1, it), Pos(w+1, it)) }
        val horizBarriers = (-1 .. w+1).flatMap { listOf(Pos(it, -1), Pos(it, h+1)) }
        val barriers = vertBarriers + horizBarriers
        return AStarWeighted.shortestPath(Pos(0,0), target, barriers.toPersistentSet(), map.toPersistentMap()).drop(1).sumOf { map[it]!! }
    }

    fun expandMap(map: Map<Pos, Int>): Map<Pos, Int> {
        val startWidth = map.keys.maxByOrNull { it.x }!!.x + 1
        val startHeight = map.keys.maxByOrNull { it.y }!!.y + 1
        val stretchedX: Map<Pos, Int> = (1 until 5).fold(map) { acc, i ->
            acc + map.map { (p, v) ->
                Pos(p.x + startWidth*i, p.y) to if (v+i > 9) (v+i) % 10 + 1 else v+i
            }
        }
        return (1 until 5).fold(stretchedX) { acc, i ->
            acc + stretchedX.map { (p, v) ->
                Pos(p.x, p.y + startHeight*i) to if (v+i > 9) (v+i) % 10 + 1 else v+i
            }
        }
    }

    fun pt1(input: List<String>): Int = findCheapestPath(parseInput(input))

    fun pt2(input: List<String>): Int = findCheapestPath(expandMap(parseInput(input)))

}