import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2110: Spek({

    val testInput = readLines("10_test.txt")
    val realInput  = readLines("10_real.txt")

    group("pt1") {

        test("with test input") {
            day10.pt1(testInput) `should be equal to` 26397
        }

        test("with real input") {
            day10.pt1(realInput) `should be equal to` 318081
        }

    }

    group("pt2") {

        test("with test input") {
            day10.pt2(testInput) `should be equal to` 288957
        }

        test("with real input") {
            day10.pt2(realInput) `should be equal to` 4361305341
        }

    }

})