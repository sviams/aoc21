object day8 {

    val DIGITS = mapOf("cf" to 1, "acdeg" to 2, "acdfg" to 3, "bcdf" to 4, "abdfg" to 5, "abdefg" to 6, "acf" to 7, "abcdefg" to 8, "abcdfg" to 9, "abcefg" to 0)

    fun pt1(input: List<String>): Int =
        input.map { it.split(" | ").last() }
            .sumOf { line -> line.split(" ")
                .count { listOf(2,3,4,7).contains(it.length) } }

    fun decodeKeys(input: List<String>): Map<Char, Char> {
        val one = input.first { it.length == 2 }
        val seven = input.first { it.length == 3 }
        val four = input.first { it.length == 4 }
        val a = seven.filter { !one.contains(it) }.first()
        val twoThreeFive = input.filter { it.length == 5 }
        val three = twoThreeFive.first { word -> one.all { c -> word.contains(c) } }
        val b = four.filter { c -> !three.contains(c) }.first()
        val g = three.filter { it != a && !four.contains(it) }.first()
        val five = twoThreeFive.first { word -> word.contains(b) }
        val f = one.filter { five.contains(it) }.first()
        val c = one.filter { !five.contains(it) }.first()
        val d = five.filter { !listOf(a,b,f,g).contains(it) }.first()
        val two = twoThreeFive.minus(three).minus(five).first()
        val e = two.filter { !listOf(a,c,d,g).contains(it) }.first()
        return mapOf(a to 'a', b to 'b', c to 'c', d to 'd', e to 'e', f to 'f', g to 'g')
    }

    fun wordToNumber(word: String, keys: Map<Char, Char>): Int {
        val sorted = word.map { keys[it] }.sortedBy { it!!.code }.joinToString("")
        return DIGITS[sorted]!!
    }

    fun pt2(input: List<String>): Long =
        input.fold(0) { acc, line ->
            val (i,o) = line.split(" | ")
            acc + o.split(" ").map { wordToNumber(it, decodeKeys(i.split(" "))) }.joinToString("").toInt()
        }
}