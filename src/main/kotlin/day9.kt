object day9 {

    val CARTESIANS = listOf(Pos.EAST, Pos.WEST, Pos.NORTH, Pos.SOUTH)

    fun neighbors(to: Pos, width: Int, height: Int): List<Pos> = CARTESIANS.map { Pos(to.x+it.x, to.y+it.y) }.filter { it.y in 0 until height && it.x in 0 until width }

    fun allPos(spots: List<List<Int>>): List<Pos> = spots.indices.flatMap { y -> spots.first().indices.map { x -> Pos(x,y) } }

    fun findLowSpots(spots: List<List<Int>>): List<Pos> = allPos(spots).filter { spot -> neighbors(spot, spots.first().size, spots.size).all { n -> spots[n.y][n.x] > spots[spot.y][spot.x] } }

    fun calcBasin(spots: List<List<Int>>, toCheck: Pos, checked: List<Pos>, width: Int, height: Int): List<Pos> {
        val neighborsToCheck = neighbors(toCheck, width, height).filter { !checked.contains(it) && spots[it.y][it.x] != 9 }
        return neighborsToCheck.fold(listOf(toCheck)) { acc, pos -> acc.plus(calcBasin(spots, pos, (checked + neighborsToCheck + acc), width, height)) }
    }

    fun pt1(input: List<String>): Int {
        val map = input.map { it.toList().map { it.digitToInt() } }
        return findLowSpots(map).sumOf { map[it.y][it.x]+1 }
    }

    fun pt2(input: List<String>): Int {
        val map = input.map { it.toList().map { it.digitToInt() } }
        return findLowSpots(map).map { calcBasin(map, it, emptyList(), map.first().size, map.size) }
            .map { it.distinct().size }.sorted().takeLast(3).fold(1, Int::times)
    }

}