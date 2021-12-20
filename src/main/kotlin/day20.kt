import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf

object day20 {

    fun parseInput(input: List<String>): Pair<Map<Int, Char>, PersistentMap<Pos, Char>> {
        val algorithm = input.first().mapIndexed { index, c -> index to c }.toMap()
        val image = input.drop(2).foldIndexed(persistentMapOf<Pos, Char>()) { row, acc, line ->
            acc.putAll(line.foldIndexed(persistentMapOf()) { col, colAcc, c ->
                colAcc.put(Pos(col,row), c)
            })
        }
        return algorithm to image
    }

    fun Pos.surrounding(): List<Pos> = (-1 .. 1).flatMap { dy -> (-1 .. 1).map { dx -> Pos(x+dx,y+dy) } }

    fun getPixelValue(pos: Pos, algorithm: Map<Int, Char>, image: PersistentMap<Pos, Char>, isFlipped: Boolean): Char =
        algorithm[pos.surrounding()
            .map { image.getOrDefault(it, if (isFlipped) '#' else '.') }
            .map { if (it == '#') 1 else 0 }
            .joinToString("")
            .toInt(2)]!!

    fun evolveImage(image: PersistentMap<Pos, Char>, algorithm: Map<Int, Char>, targetIterations: Int, iterations: Int = 0): PersistentMap<Pos, Char> {
        if (iterations == targetIterations) return image
        val doesFlip = algorithm[0] == '#'
        val min = image.keys.first()
        val max = image.keys.last()
        val newImage = (min.y-1 .. max.y+1).fold(persistentMapOf<Pos, Char>()) { rowAcc, row ->
            val allInRow = (min.x-1 .. max.x+1).fold(persistentMapOf<Pos, Char>()) { colAcc, col ->
                val p = Pos(col, row)
                val pixelValue = getPixelValue(p, algorithm, image, doesFlip && (iterations + 1) % 2 == 0)
                colAcc.put(p, pixelValue)
            }
            rowAcc.putAll(allInRow)
        }
        return evolveImage(newImage, algorithm, targetIterations, iterations + 1)
    }

    fun solve(input: List<String>, iterations: Int): Int {
        val (algorithm, image) = parseInput(input)
        return evolveImage(image, algorithm, iterations).count { it.value == '#' }
    }

    fun pt1(input: List<String>): Int = solve(input, 2)

    fun pt2(input: List<String>): Int = solve(input, 50)
}