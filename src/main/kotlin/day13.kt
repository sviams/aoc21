object day13 {

    fun printGrid(grid: List<List<Int>>) {
        println()
        grid.forEach { r ->
            println()
            r.forEach { c -> if (c == 1) print('#') else print(' ') }
        }
    }

    fun parseInput(input: List<String>): Pair<List<List<Int>>, List<Pair<String, Int>>> {
        val positions = input.takeWhile { it.isNotEmpty() }.map { line ->
            val (col, row) = line.split(",")
            Pos(col.toInt(), row.toInt())
        }
        val folds = input.dropWhile { it.isNotEmpty() }.drop(1).map { line ->
            val (axis, index) = line.drop(11).split("=")
            axis to index.toInt()
        }
        val grid = (0 .. positions.maxByOrNull { it.y }!!.y).map { row ->
            (0 .. positions.maxByOrNull { it.x }!!.x).map { col -> if (positions.contains(Pos(col,row))) 1 else 0 }
        }
        return grid to folds
    }

    fun foldY(grid: List<List<Int>>, index: Int): List<List<Int>> {
        val toKeep = grid.take(index)
        val remaining = grid.drop(index+1)
        val diff = toKeep.size - remaining.size
        val padding = (0 until diff).map { (0 .. grid.first().size).map { 0 } }
        val toFlip = remaining.plus(padding).reversed()
        return toKeep.mapIndexed { row, line ->
            line.mapIndexed { col, d -> if (d + toFlip[row][col] > 0) 1 else 0 }
        }
    }

    fun foldX(grid: List<List<Int>>, index: Int): List<List<Int>> {
        val toKeep = grid.map { line -> line.take(index) }
        val toFlip = grid.map { line -> line.drop(index+1).reversed() }
        return toKeep.mapIndexed { row, line ->
            line.mapIndexed { col, d -> if (d + toFlip[row][col] > 0) 1 else 0 }
        }
    }

    tailrec fun fold(grid: List<List<Int>>, folds: List<Pair<String, Int>>): List<List<Int>> {
        if (folds.isEmpty()) return grid
        val nextFold = folds.first()
        val nextGrid = if (nextFold.first == "x") foldX(grid, nextFold.second) else foldY(grid, nextFold.second)
        return fold(nextGrid, folds.drop(1))
    }

    fun pt1(input: List<String>): Int {
        val (grid, folds) = parseInput(input)
        return fold(grid, folds.take(1)).sumOf { it.sum() }
    }

    fun pt2(input: List<String>): Int {
        val (grid, folds) = parseInput(input)
        printGrid(fold(grid, folds))
        return -1
    }

}