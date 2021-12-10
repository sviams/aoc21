object day10 {

    val pairs = mapOf('(' to ')', '{' to '}', '<' to '>', '[' to ']')
    val score = mapOf(')' to 3, '}' to 1197, '>' to 25137, ']' to 57)
    val autoScore = mapOf(')' to 1, '}' to 3, '>' to 4, ']' to 2)

    fun analyze(line: String): Pair<String, String> =
        line.fold("" to "") { (stack, illegal), c ->
            when (c) {
                in pairs.keys -> stack + c to illegal
                pairs[stack.last()] -> stack.dropLast(1) to illegal
                else -> stack to illegal + c
            }
        }

    fun pt1(input: List<String>): Int =
        input.map { analyze(it) }.filter { it.second.isNotEmpty() }.map { score[it.second.first()]!! }.sum()

    fun pt2(input: List<String>): Long {
        val scores = input.map { analyze(it) }
            .filter { it.second.isEmpty() }
            .map { it.first.reversed().map { pairs[it] } }
            .map { line -> line.fold(0L) { acc, c -> acc * 5 + autoScore[c]!! } }
        return scores.sorted()[scores.size / 2]
    }

}