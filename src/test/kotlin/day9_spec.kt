import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2109: Spek({

    val testInput = readLines("9_test.txt")
    val realInput  = readLines("9_real.txt")

    group("pt1") {

        test("with test input") {
            day9.pt1(testInput) `should be equal to` 15
        }

        test("with real input") {
            day9.pt1(realInput) `should be equal to` 475
        }

    }

    group("pt2") {

        test("with test input") {
            day9.pt2(testInput) `should be equal to` 1134
        }

        test("with real input") {
            day9.pt2(realInput) `should be equal to` 1092012
        }

    }

})