import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

object day24 {

    val validDigitsForW = (1 .. 9)

    // This is what each of the 14 blocks of 18 lines in the input actually do
    fun calcZ(w: Int, z: Int, divZ: Int, addX: Int, addY: Int): Int = if ((z % 26 + addX) != w) z / divZ * 26 + w + addY else z / divZ

    fun findHighestAndLowest(input: List<String>): Pair<Long, Long> {
        // The code repeats 14 times, with differences in these three instructions
        val blockValues: List<List<Int>> = input.chunked(18).map { c ->
            val (divZStr) = """div z (\S+)""".toRegex().find(c[4])!!.destructured
            val (addXStr) = """add x (\S+)""".toRegex().find(c[5])!!.destructured
            val (addYStr) = """add y (\S+)""".toRegex().find(c[15])!!.destructured
            listOf(divZStr.toInt(), addXStr.toInt(), addYStr.toInt())
        }

        // For every possible digit in every possible position, calculate the values of Z that would be valid as input
        // Start in reverse as we know the acceptable range of Z there is 0, then feed back valid z into the next iteration
        val validZsPerWPerIndex: List<PersistentMap<Int, List<Int>>> = blockValues.reversed().foldIndexed(emptyList<PersistentMap<Int, List<Int>>>()) { index, constraints, (divZ, addX, addY) ->
            val zRange =  constraints.lastOrNull()?.values?.flatten() ?:  setOf(0)
            val maxZ = 11881376 // Highest possible is a power of 26, decreasing towards the end, this was first power found that gave needed inputs
            val validZsForAllW: PersistentMap<Int, List<Int>> = validDigitsForW.fold(persistentMapOf()) { acc, w ->
                val validZsForThisW = (0 .. maxZ).fold(persistentListOf<Int>()) { zacc, z ->
                    val testZ = calcZ(w, z, divZ, addX, addY)
                    if (testZ in zRange) zacc.add(z) else zacc
                }.distinct()
                acc.put(w, validZsForThisW)
            }
            println("$index - ${validZsForAllW.values.flatten().size} ($maxZ)")
            constraints + validZsForAllW
        }

        val serials = findSerial(0,0,validZsPerWPerIndex.reversed(), blockValues).map { it.toLong() }
        return serials.maxOrNull()!! to serials.minOrNull()!!
    }

    // Recursively construct valid numbers using the map of valid Zs constructed before
    fun findSerial(index: Int, z: Int, cheatSheet: List<Map<Int, List<Int>>>, blockValues: List<List<Int>>): List<String> {
        if (index == 14) return listOf("")

        val validZsPerW = cheatSheet.get(index)
        val possibleWs = validZsPerW.filter { z in it.value }.map { it.key }
        return possibleWs.flatMap { w ->
            val (divZ, addX, addY) = blockValues[index]
            val nextZ = calcZ(w, z, divZ, addX, addY)
            findSerial(index+1, nextZ, cheatSheet, blockValues).map { w.toString() + it }
        }
    }

    fun pt1(input: List<String>): Long = findHighestAndLowest(input).first

    fun pt2(input: List<String>): Long = findHighestAndLowest(input).first

}
