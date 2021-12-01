import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2101: Spek({

    val testInput = readLines("1_test.txt").map { it.toInt() }
    val realInput  = readLines("1_real.txt").map { it.toInt() }

    group("pt1") {

        test("with test input") {
            day1.pt1(testInput) `should be equal to` 7
        }

        test("with real input") {
            day1.pt1(realInput) `should be equal to` 1564
        }

    }

    group("pt2") {

        test("with test input") {
            day1.pt2(testInput) `should be equal to` 5
        }

        test("with real input") {
            day1.pt2(realInput) `should be equal to` 1611
        }

    }

})