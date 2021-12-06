import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2106: Spek({

    val testInput = readLines("6_test.txt")
    val realInput  = readLines("6_real.txt")

    group("pt1") {

        test("with test input") {
            day6.pt1(testInput) `should be equal to` 5934
        }

        test("with real input") {
            day6.pt1(realInput) `should be equal to` 380758
        }

    }

    group("pt2") {

        test("with test input") {
            day6.pt2(testInput) `should be equal to` 26984457539
        }

        test("with real input") {
            day6.pt2(realInput) `should be equal to` 1710623015163
        }

    }

})