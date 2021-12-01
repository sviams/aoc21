object day1 {

    fun pt1(input: List<Int>): Int =
        input.windowed(2, 1, false) { window -> window.first() < window.last() }
            .count { it }

    fun pt2(input: List<Int>): Int =
        input.windowed(3, 1, false) { window -> window.sum() }
            .windowed(2, 1, false) { window -> window.first() < window.last() }
            .count { it }

}