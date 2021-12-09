object day9 {

    val CARTESIANS = listOf(Pos.EAST, Pos.WEST, Pos.NORTH, Pos.SOUTH)

    tailrec fun findLowSpots(spots: List<List<Int>>, result: List<Pos> = emptyList(), row: Int = 0, col: Int = 0): List<Pos> {
        val width = spots.first().size
        val height = spots.size
        if (row == height-1 && col == width-1) return result

        val neighbors = CARTESIANS.map { row+it.y to col+it.x }
            .filter { it.first in 0 until height && it.second >= 0 && it.second < width }
            .map { spots[it.first][it.second] }

        val newResult = if (spots[row][col] < neighbors.minOrNull()!!) result + Pos(col, row) else result
        val newCol = if (col == width -1) 0 else col + 1
        val newRow = if (newCol == 0) row + 1 else row
        return findLowSpots(spots, newResult, newRow, newCol)
    }

    fun calcBasin(spots: List<List<Int>>, toCheck: Pos, checked: List<Pos>, width: Int, height: Int): List<Pos> {
        val neighborsToCheck = CARTESIANS.map { Pos(toCheck.x+it.x,toCheck.y+it.y) }.filter { it.y in 0 until height && it.x in 0 until width && !checked.contains(it) && spots[it.y][it.x] != 9 }
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