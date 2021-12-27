object day25 {

    fun inc(current: Int, limit: Int): Int = (current + 1) % limit
    fun dec(current: Int, limit: Int) = if (current == 0) limit-1 else current - 1

    fun moveRight(line: String): String =
        line.mapIndexed { index, c ->
            val toLeft = line[dec(index, line.length)]
            val toRight = line[inc(index, line.length)]
            if (c == '.' && toLeft == '>') '>'
            else if (c == '>' && toRight == '.') '.'
            else c
        }.joinToString("")

    fun moveDown(map: List<String>): List<String> =
        map.indices.map { row -> map.first().indices.map { col ->
            val atPos = map[row][col]
            val above = map[dec(row, map.size)][col]
            val below = map[inc(row, map.size)][col]
            if (atPos == '.' && above == 'v') 'v'
            else if (atPos == 'v' && below == '.') '.'
            else atPos
        }.joinToString("") }

    tailrec fun evolve(map: List<String>, steps: Int = 0): Int {
        val movedRight = map.map { moveRight(it) }
        val movedDown = moveDown(movedRight)
        return if (movedDown == map) steps+1 else evolve(movedDown, steps+1)
    }

    fun pt1(input: List<String>): Int = evolve(input)
}
