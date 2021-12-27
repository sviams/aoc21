object day23 {

    val linksWithStepsShort = mapOf(
        0 to mapOf(1 to 1),
        1 to mapOf(0 to 1,11 to 2,20 to 2),

        11 to mapOf(1 to 2, 20 to 2, 12 to 1),
        12 to mapOf(11 to 1),

        20 to mapOf(1 to 2,11 to 2,21 to 2,30 to 2),

        21 to mapOf(20 to 2, 30 to 2, 22 to 1),
        22 to mapOf(21 to 1),

        30 to mapOf(20 to 2,21 to 2,31 to 2,40 to 2),

        31 to mapOf(30 to 2, 40 to 2, 32 to 1),
        32 to mapOf(31 to 1),

        40 to mapOf(30 to 2,31 to 2,41 to 2,50 to 2),

        41 to mapOf(40 to 2, 50 to 2, 42 to 1),
        42 to mapOf(41 to 1),

        50 to mapOf(40 to 2,41 to 2,51 to 1),
        51 to mapOf(50 to 1)
    )

    val linksWithStepsLong = mapOf(
        0 to mapOf(1 to 1),
        1 to mapOf(0 to 1,11 to 2,20 to 2),

        11 to mapOf(1 to 2, 20 to 2, 12 to 1),
        12 to mapOf(11 to 1, 13 to 1),
        13 to mapOf(12 to 1, 14 to 1),
        14 to mapOf(13 to 1),

        20 to mapOf(1 to 2,11 to 2,21 to 2,30 to 2),

        21 to mapOf(20 to 2, 30 to 2, 22 to 1),
        22 to mapOf(21 to 1, 23 to 1),
        23 to mapOf(22 to 1, 24 to 1),
        24 to mapOf(23 to 1),

        30 to mapOf(20 to 2,21 to 2,31 to 2,40 to 2),

        31 to mapOf(30 to 2, 40 to 2, 32 to 1),
        32 to mapOf(31 to 1, 33 to 1),
        33 to mapOf(32 to 1, 34 to 1),
        34 to mapOf(33 to 1),

        40 to mapOf(30 to 2,31 to 2,41 to 2,50 to 2),

        41 to mapOf(40 to 2, 50 to 2, 42 to 1),
        42 to mapOf(41 to 1, 43 to 1),
        43 to mapOf(42 to 1, 44 to 1),
        44 to mapOf(43 to 1),

        50 to mapOf(40 to 2,41 to 2,51 to 1),
        51 to mapOf(50 to 1)
    )

    val hallways = listOf(0,1,20,30,40,50,51)

    val trenchLvl1 = listOf(11,21,31,41)
    val trenchLvl2 = listOf(12,22,32,42)
    val trenchLvl3 = listOf(13,23,33,43)
    val trenchLvl4 = listOf(14,24,34,44)

    val desiredShort = mapOf(
        'A' to listOf(11,12),
        'B' to listOf(21,22),
        'C' to listOf(31,32),
        'D' to listOf(41,42),
    )

    val desiredLong = mapOf(
        'A' to listOf(11,12,13,14),
        'B' to listOf(21,22,23,24),
        'C' to listOf(31,32,33,34),
        'D' to listOf(41,42,43,44),
    )

    data class Cave(val map: Map<Int, Char?>, val isShort: Boolean) {
        fun isComplete(): Boolean = if (isShort) desiredShort.all { (c, spots) -> spots.all { s -> map[s] == c } } else desiredLong.all { (c, spots) -> spots.all { s -> map[s] == c } }
        fun isEmpty(pos: Int) = map[pos] == null
        fun isInEndState(pos: Int) = when (map[pos]) {
            'A' -> pos in 11..14
            'B' -> pos in 21..24
            'C' -> pos in 31..34
            'D' -> pos in 41..44
            else -> false
        }
        fun costOfMoving(pos: Int): Int = when (map[pos]) {
            'A' -> 1
            'B' -> 10
            'C' -> 100
            'D' -> 1000
            else -> throw IllegalStateException("Oops2")
        }

        fun move(from: Int, to: Int): Cave = Cave(map.plus(to to map[from]).plus(from to null), isShort)

        fun pathCost(from: Int, to: Int, wrongTrenches: List<Int>, correctTrench: List<Int>, cost: Int = 0): Int {
            val possible = if (isShort) linksWithStepsShort[from]!! else linksWithStepsLong[from]!!
            if (possible.containsKey(to) && isEmpty(to)) return cost + possible[to]!!
            val okRange = if (to > from) from until to else to-5 until from
            val inRightDirection: Map<Int, Int> = possible.filter {
                it.key !in wrongTrenches && isEmpty(it.key) && it.key in okRange
            }
            if (inRightDirection.isEmpty()) return Int.MAX_VALUE
            return inRightDirection.minOf { pathCost(it.key, to, wrongTrenches, correctTrench, cost + it.value) }
        }

        fun isCorrectState(pos: Int): Boolean =
            if (isShort) when (pos) {
                11 -> listOf(11,12).all { isInEndState(it) }
                12 -> isInEndState(12)
                21 -> listOf(21,22).all { isInEndState(it) }
                22 -> isInEndState(22)
                31 -> listOf(31,32).all { isInEndState(it) }
                32 -> isInEndState(32)
                41 -> listOf(41,42).all { isInEndState(it) }
                42 -> isInEndState(42)
                else -> false
            }
            else when (pos) {
                11 -> listOf(11,12,13,14).all { isInEndState(it) }
                12 -> listOf(12,13,14).all { isInEndState(it) }
                13 -> listOf(13,14).all { isInEndState(it) }
                14 -> isInEndState(14)
                21 -> listOf(21,22,23,24).all { isInEndState(it) }
                22 -> listOf(22,23,24).all { isInEndState(it) }
                23 -> listOf(23,24).all { isInEndState(it) }
                24 -> isInEndState(24)
                31 -> listOf(31,32,33,34).all { isInEndState(it) }
                32 -> listOf(32,33,34).all { isInEndState(it) }
                33 -> listOf(33,34).all { isInEndState(it) }
                34 -> isInEndState(34)
                41 -> listOf(41,42,43,44).all { isInEndState(it) }
                42 -> listOf(42,43,44).all { isInEndState(it) }
                43 -> listOf(43,44).all { isInEndState(it) }
                44 -> isInEndState(44)
                else -> false
            }

        fun restingSpot(trench: List<Int>): Int = trench.reversed().takeWhileInclusive { isInEndState(it) }.last()

        fun movesFromTrench(from: Int, offset: Int): List<Pair<Cave, Int>> {
            val correctTrench = if (isShort) desiredShort[map[from]]!! else desiredLong[map[from]]!!
            val correctDest = restingSpot(correctTrench)
            val openPaths: Map<Int, Int> = (hallways + correctDest).filter { isEmpty(it) }.associateWith { pathCost(from-offset, it, trenchLvl1.minus(correctTrench), correctTrench) }.filter { it.value < 1000 }.map { it.key to it.value+offset }.toMap()
            return openPaths.entries.map { move(from, it.key) to costOfMoving(from)*it.value }
        }

        fun possibleMoves(): List<Pair<Cave, Int>> =
            (if (isShort) linksWithStepsShort else linksWithStepsLong).flatMap { (from, toList) ->
                val inFrom = map[from]
                if (inFrom == null) emptyList()
                else if (isCorrectState(from)) emptyList()
                else if (from in hallways) {
                    val correctTrench = if (isShort) desiredShort[inFrom]!! else desiredLong[inFrom]!!
                    val correctDest = restingSpot(correctTrench)
                    val wrongTrenches = trenchLvl1.minus(correctTrench)
                    val stepsToEndState = pathCost(from, correctDest, wrongTrenches, correctTrench)
                    if (stepsToEndState < 1000) listOf(move(from, correctDest) to costOfMoving(from)*stepsToEndState) else emptyList()
                }
                else if (from in trenchLvl1 ) movesFromTrench(from,0)
                else if (from in trenchLvl2 && isEmpty(from-1)) movesFromTrench(from,1)
                else if (!isShort && from in trenchLvl3 && isEmpty(from-1) && isEmpty(from-2)) movesFromTrench(from,2)
                else if (!isShort && from in trenchLvl4 && isEmpty(from-1) && isEmpty(from-2) && isEmpty(from-3)) movesFromTrench(from,3)
                else emptyList()
            }
    }

    // I know, this is ugly...
    val mutMemo: MutableMap<Cave, Int> = mutableMapOf()

    fun traverse(state: Cave, cost: Int = 0): Int {
        if (state.isComplete()) return cost

        val goodMoves = state.possibleMoves().filter { (move, moveCost) -> mutMemo.getOrDefault(move, Int.MAX_VALUE) > cost + moveCost }
        mutMemo.putAll(goodMoves.map { it.first to it.second + cost })

        return goodMoves.fold(Int.MAX_VALUE) { best, (m,c) ->
            val newState = traverse(m, cost + c)
            if (newState < best) newState else best
        }

    }

    fun pt1(input: Cave): Int = traverse(input)

    fun pt2(input: Cave): Int = traverse(input)

}

