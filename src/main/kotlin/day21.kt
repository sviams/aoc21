object day21 {

    data class GameState(val p1: Int, val p2: Int, val endCondition: Int = 21, val p1Score: Int = 0, val p2Score: Int = 0, val isP1Turn: Boolean = true) {
        fun isP1Winner() = p1Score >= endCondition
        fun isP2Winner() = p2Score >= endCondition
        fun isEnded() = isP1Winner() || isP2Winner()
        fun advance(die: Int): GameState {
            val newP1Pos = if (isP1Turn) ((p1 + die -1) % 10) + 1 else p1
            val newP1Score = if (isP1Turn) p1Score + newP1Pos else p1Score
            val newP2Pos = if (!isP1Turn) ((p2 + die -1) % 10) + 1 else p2
            val newP2Score = if (!isP1Turn) p2Score + newP2Pos else p2Score
            return GameState(newP1Pos, newP2Pos, endCondition, newP1Score, newP2Score, !isP1Turn)
        }
    }

    val combos = (1..3).flatMap { a -> (1..3).flatMap { b -> (1..3).map { c -> listOf(a,b,c) } } }.map { it.sum() }
    val counts = combos.distinct().map { d -> combos.count { it == d }.toLong() }
    val rolls = combos.distinct().zip(counts) // Possible triple roll combinations and their frequencies

    fun playDiracRecursive(gs: GameState): Pair<Long, Long> =
            rolls.map { (die, count) ->
                val newState = gs.advance(die)
                if (gs.isEnded()) return if (gs.isP1Winner()) count to 0L else 0L to count
                else {
                    val next = playDiracRecursive(newState)
                    next.first * count to next.second * count
                }
            }.reduce { (accA, accB), (a,b) -> accA + a to accB + b }

    fun pt1(p1: Int, p2: Int): Int {
        val (rounds, endState) = generateSequence(GameState(p1,p2, 1000) to 1) { (gs, die) ->
            val roll = (die % 100) + ((die) % 100 )+1 + ((die+1) % 100)+1
            gs.advance(roll) to ((die + 2) % 100) + 1
        }.withIndex().takeWhileInclusive { !it.value.first.isEnded() }.last()
        val endScore = if (endState.first.isP1Winner()) endState.first.p2Score else endState.first.p1Score
        return endScore * rounds * 3
    }

    fun pt2(p1: Int, p2: Int): Long {
        val (p1Wins, p2Wins) = playDiracRecursive(GameState(p1,p2))
        return if (p1Wins > p2Wins) p1Wins else p2Wins
    }
}
