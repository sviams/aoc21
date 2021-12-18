import java.lang.Math.ceil

object day18 {

    fun explode(s: String): String {
        val (startIndex, _) = s.foldIndexed(Pair(-1,0)) { index: Int, acc: Pair<Int, Int>, c: Char ->
            val newCount = if (c == '[') acc.second + 1 else if (c == ']') acc.second - 1 else acc.second
            if (newCount == 5 && acc.first == -1) index to newCount else acc.first to newCount
        }
        if (startIndex == -1) return s
        val (exploLeft, exploRight) = """\[(\d+),(\d+)""".toRegex().find(s.drop(startIndex))!!.destructured
        val exploLength = 3 + exploLeft.length + exploRight.length

        val numbersToTheLeft = s.take(startIndex).replace('[', ',').replace(']', ',').split(",").filter { it.isNotEmpty() }
        val leftNumber = if (numbersToTheLeft.isEmpty()) -1 else numbersToTheLeft.last().toInt()
        val charsBetweenExploAndLeftNumber = s.take(startIndex).reversed().takeWhile { !it.isDigit() }.length
        val leftNumberIndex = startIndex - charsBetweenExploAndLeftNumber - leftNumber.toString().length

        val numbersToTheRight = s.drop(startIndex + exploLength).replace('[', ',').replace(']', ',').split(",").filter { it.isNotEmpty() }
        val rightNumber = if (numbersToTheRight.isEmpty()) -1 else numbersToTheRight.first().toInt()
        val charsBetweenExploAndRightNumber = s.drop(startIndex + exploLength).takeWhile { !it.isDigit() }.length

        val newLeft = (leftNumber+exploLeft.toInt())
        val newRight = (rightNumber+exploRight.toInt())

        val leftReplaced = if (leftNumber == -1) s else s.take(leftNumberIndex) + newLeft + s.drop(leftNumberIndex + leftNumber.toString().length)
        val newStartIndex = if (leftNumber == -1) startIndex else startIndex - leftNumber.toString().length + newLeft.toString().length
        val rightNumberIndex = newStartIndex + exploLength + charsBetweenExploAndRightNumber
        val rightReplaced = if (rightNumber == -1) leftReplaced else leftReplaced.take(rightNumberIndex) + newRight + leftReplaced.drop(rightNumberIndex + rightNumber.toString().length)
        return rightReplaced.take(newStartIndex) + '0' + rightReplaced.drop(newStartIndex + exploLength)
    }

    fun split(s: String): String {
        val firstDoubleDigit = """(\d\d)""".toRegex().find(s)?.value ?: return s
        val index = s.indexOf(firstDoubleDigit)
        val l = firstDoubleDigit.toInt() / 2
        val r = ceil(firstDoubleDigit.toDouble()/2).toInt()
        return s.take(index) + "[$l,$r]" + s.drop(index + firstDoubleDigit.length)
    }

    fun add(a: String, b: String): String = "[$a,$b]"

    tailrec fun reduce(s: String): String {
        val exploded = explode(s)
        if (exploded != s) return reduce(exploded)
        val split = split(s)
        return if (split == s) s else reduce(split)
    }

    fun addList(input: List<String>): String = input.drop(1).fold(input.first()) { acc, line -> reduce(add(acc, line)) }

    tailrec fun calcMagnitude(s: String): Int {
        val match = """(\d+),(\d+)""".toRegex().find(s) ?: return s.toInt()
        val (l,r) = match.destructured
        val newNumber = 3*l.toInt() + 2*r.toInt()
        val indexOfGroup = s.indexOf("[$l,$r]")
        return calcMagnitude(s.take(indexOfGroup) + newNumber + s.drop(indexOfGroup + 3 + l.length + r.length))
    }

    fun pt1(input: List<String>): Int = calcMagnitude(addList(input))

    fun pt2(input: List<String>): Int =
        input.fold(0) { best, line ->
            val bestForLine = input.minus(line).fold(0) { lineBest, other ->
                val lineMagnitude = calcMagnitude(reduce(add(line, other)))
                if (lineMagnitude > lineBest) lineMagnitude else lineBest
            }
            if (bestForLine > best) bestForLine else best
        }
}