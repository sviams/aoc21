object day4 {

    data class Board(val rows: List<List<Int>>) {
        fun hasBingo(marked: List<Int>): Boolean = rows.any { row -> row.all { number -> marked.contains(number) } } || columns.any { col -> col.all { number -> marked.contains(number) } }
        val columns: List<List<Int>> by lazy { rows.indices.map { index -> rows.map { line -> line[index] } } }
        fun unmarked(marked: List<Int>): List<Int> = rows.fold(emptyList()) { acc, line -> acc + line.filter { number -> !marked.contains(number) } }
    }

    tailrec fun playBingo(boards: List<Board>, numbers: List<Int>, marked: List<Int> = emptyList()): Int {
        return if (boards.any { it.hasBingo(marked) })
            boards.first { it.hasBingo(marked) }.unmarked(marked).sum() * marked.last()
        else playBingo(boards, numbers.drop(1), marked + numbers.first())
    }

    tailrec fun playBingoUntilLast(boards: List<Board>, numbers: List<Int>, marked: List<Int> = emptyList()): Int {
        return if (boards.size == 1 && boards.first().hasBingo(marked)) boards.first().unmarked(marked).sum() * marked.last()
        else playBingoUntilLast(boards.filter { !it.hasBingo(marked) }, numbers.drop(1), marked + numbers.first())
    }

    fun parseBoards(input: List<String>): List<Board> = input.drop(1).chunked(6).map { chunk ->
        Board(chunk.drop(1).map { line ->
            line.trim().split("""\s+""".toRegex()).map { number -> number.toInt() }
        } )
    }

    fun pt1(input: List<String>): Int {
        val numbers = input.first().split(",").map { it.toInt() }
        val boards = parseBoards(input)
        return playBingo(boards, numbers)
    }

    fun pt2(input: List<String>): Int {
        val numbers = input.first().split(",").map { it.toInt() }
        val boards = parseBoards(input)
        return playBingoUntilLast(boards, numbers)
    }


}