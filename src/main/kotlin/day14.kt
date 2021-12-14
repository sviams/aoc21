import kotlinx.collections.immutable.*

object day14 {

    fun parseInput(input: List<String>): Pair<String, Map<String, String>> {
        val polymer = input.takeWhile { it.isNotEmpty() }.first()
        val recipes = input.dropWhile { it.isNotEmpty() }.drop(1).associate { line ->
            val (pattern, result) = line.split(" -> ")
            pattern to result
        }
        return polymer to recipes
    }

    tailrec fun evolve(map: PersistentMap<String, Long>, recipes: Map<String, String>, targetIterations: Int = 10, iterations: Int = 0): PersistentMap<String, Long> {
        if (iterations == targetIterations) return map
        val nextMap = map.entries.fold(persistentMapOf()) { acc: PersistentMap<String, Long>, (pair: String, count: Long) ->
            val firstHalf = pair.first() + recipes[pair]!!
            val firstHalfCount = acc.getOrDefault(firstHalf, 0L) + count
            val secondHalf = recipes[pair]!! + pair.last()
            val secondHalfCount = acc.getOrDefault(secondHalf, 0L) + count
            acc.put(firstHalf, firstHalfCount).put(secondHalf, secondHalfCount)
        }
        return evolve(nextMap, recipes, targetIterations, iterations+1)
    }

    fun solve(input: List<String>, iterations: Int): Long {
        val (polymer, recipes) = parseInput(input)
        val startPairs = polymer.windowed(2).fold(persistentMapOf()) { acc: PersistentMap<String, Long>, s: String ->
            acc.put(s, acc.getOrDefault(s, 0)+1)
        }
        val endPairs = evolve(startPairs, recipes, iterations)
        val characterFrequencies = endPairs.entries.fold(persistentMapOf<Char, Long>()) { acc, (pair, count) ->
            acc.put(pair.first(), acc.getOrDefault(pair.first(), 0L) + count)
        }
        val adjustedForEnd = characterFrequencies.put(polymer.last(), characterFrequencies[polymer.last()]!!+1)
        return adjustedForEnd.maxOf { it.value } - adjustedForEnd.minOf { it.value }
    }

    fun pt1(input: List<String>): Long = solve(input, 10)

    fun pt2(input: List<String>): Long = solve(input, 40)

}