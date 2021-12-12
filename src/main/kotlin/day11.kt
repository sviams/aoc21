import kotlinx.collections.immutable.*

object day11 {

    data class State(val grid: PersistentMap<Pos, Int>, val flashCount: Int, val iterations: Int) {
        fun increment(): State {
            val afterIncrease = grid.keys.fold(grid) {acc, pos -> acc.put(pos, acc[pos]!!+1) }
            val toFlash = afterIncrease.keys.filter { afterIncrease[it]!! > 9 }
            val (afterFlashes, newFlashed) = flashLocations(afterIncrease, toFlash)
            return State(newFlashed.fold(afterFlashes) { g, p -> g.put(p, 0) }, flashCount + newFlashed.size, iterations + 1)
        }
    }

    fun adjacent(to: Pos): List<Pos> =
        listOf(-1, 0, 1).flatMap { y -> listOf(-1, 0, 1).map { x -> Pos(to.x+x,to.y+y) } }
            .filter { it != to && it.x in 0 until 10 && it.y in 0 until 10 }

    fun parseGrid(input: List<String>): State =
        State(input.flatMapIndexed { row, line ->
            line.mapIndexed { col, c -> Pos(col, row) to c.digitToInt() }
        }.toMap().toPersistentMap(), 0, 0)

    tailrec fun flashLocations(grid: PersistentMap<Pos, Int>, toFlash: List<Pos>, flashed: List<Pos> = emptyList()): Pair<PersistentMap<Pos, Int>, List<Pos>> {
        if (toFlash.isEmpty()) return grid to flashed
        val adjacentToFlashing = toFlash.fold(emptyList<Pos>()) { acc, p -> acc + adjacent(p)}.filter { !flashed.contains(it) && !toFlash.contains(it) }
        val gridAfterFlash = adjacentToFlashing.fold(grid) { acc, adj -> acc.put(adj, acc[adj]!! + 1) }
        val triggeredNeighbors = adjacentToFlashing.filter { gridAfterFlash[it]!! > 9 }.distinct()
        return flashLocations(gridAfterFlash, triggeredNeighbors, flashed + toFlash)
    }

    fun pt1(input: List<String>): Int =
        generateSequence(parseGrid(input)) { state -> state.increment() }.take(101).last().flashCount

    fun pt2(input: List<String>): Int =
        generateSequence(parseGrid(input)) { state -> state.increment() }.takeWhileInclusive { state -> !state.grid.all { it.value == 0 } }.last().iterations

}