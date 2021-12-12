object day12 {

    fun parseInput(input: List<String>): Map<String, List<String>> =
        input.fold(emptyMap()) { acc, line ->
            val (f, t) = line.split("-")
            acc.plus(f to acc.getOrDefault(f, emptyList()).plus(t)).plus(t to acc.getOrDefault(t, emptyList()).plus(f))
        }

    fun findPaths(map: Map<String, List<String>>, hasVisitedSmallTwice: Boolean = true, path: List<String> = emptyList(), current: String = "start"): List<List<String>> {
        if (current == "end") return listOf(path + current)
        val connections = map[current]!!.filter { it != "start" }
        val newHasVisitedSmallTwice = hasVisitedSmallTwice || current.lowercase() == current && path.contains(current)
        val toVisit = if (newHasVisitedSmallTwice) connections.filter { !(it.lowercase() == it && path.contains(it)) } else connections
        return toVisit.fold(emptyList()) { acc, node -> acc + findPaths(map, newHasVisitedSmallTwice, path + current, node) }
    }

    fun pt1(input: List<String>): Int = findPaths(parseInput(input), true).size

    fun pt2(input: List<String>): Int = findPaths(parseInput(input), false).size

}