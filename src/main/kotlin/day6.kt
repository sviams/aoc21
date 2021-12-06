object day6 {

    fun parseInput(input: List<String>): Map<Int, Long> = input.first()
        .split(",")
        .map { it.toInt() }
        .fold(emptyMap()) { acc, number -> acc.plus(number to acc.getOrDefault(number,0)+1) }

    tailrec fun evolve(freqMap: Map<Int, Long>, targetIterations: Int, iteration: Int = 0): Map<Int, Long> {
        if (iteration == targetIterations) return freqMap
        val newMap = (8 downTo 0).fold(emptyMap<Int, Long>()) { acc, number ->
            val atNumber = freqMap.getOrDefault(number, 0)
            if (number == 0) {
                acc.plus(6 to freqMap.getOrDefault(7, 0) + atNumber).plus(8 to atNumber)
            } else acc.plus(number-1 to atNumber)
        }
        return evolve(newMap, targetIterations, iteration+1)
    }

    fun pt1(input: List<String>): Long = evolve(parseInput(input), 80).values.sum()

    fun pt2(input: List<String>): Long = evolve(parseInput(input), 256).values.sum()

}