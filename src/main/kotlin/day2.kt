object day2 {

    tailrec fun move(input: List<String>, horizontal: Int = 0, depth: Int = 0): Pair<Int, Int> {
        if (input.isEmpty()) return horizontal to depth
        val (direction, amount) = """(\S+) (\d+)""".toRegex().find(input.first())!!.destructured
        return when (direction) {
            "forward" -> move(input.drop(1), horizontal + amount.toInt(), depth)
            "up" -> move(input.drop(1), horizontal, depth - amount.toInt())
            else -> move(input.drop(1), horizontal, depth + amount.toInt())
        }
    }

    tailrec fun moveWithAim(input: List<String>, horizontal: Int = 0, depth: Int = 0, aim: Int = 0): Pair<Int, Int> {
        if (input.isEmpty()) return horizontal to depth
        val (direction, amount) = """(\S+) (\d+)""".toRegex().find(input.first())!!.destructured
        return when (direction) {
            "forward" -> moveWithAim(input.drop(1), horizontal + amount.toInt(), depth + (aim * amount.toInt()), aim)
            "up" -> moveWithAim(input.drop(1), horizontal, depth, aim - amount.toInt())
            else -> moveWithAim(input.drop(1), horizontal, depth, aim + amount.toInt())
        }
    }

    fun pt1(input: List<String>): Int {
        val (horizontal, depth) = move(input)
        return horizontal * depth
    }

    fun pt2(input: List<String>): Int {
        val (horizontal, depth) = moveWithAim(input)
        return horizontal * depth
    }

}