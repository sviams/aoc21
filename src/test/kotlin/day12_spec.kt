import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2112: Spek({

    val testInput = readLines("12_test.txt")
    val testInput2 = readLines("12_test2.txt")
    val testInput3 = readLines("12_test3.txt")
    val realInput  = readLines("12_real.txt")

    group("pt1") {

        test("with test input") {
            day12.pt1(testInput) `should be equal to` 10
        }

        test("with test input 2") {
            day12.pt1(testInput2) `should be equal to` 19
        }

        test("with test input 3") {
            day12.pt1(testInput3) `should be equal to` 226
        }

        test("with real input") {
            day12.pt1(realInput) `should be equal to` 4573
        }

    }

    group("pt2") {

        test("with test input") {
            day12.pt2(testInput) `should be equal to` 36
        }

        test("with test input 2") {
            day12.pt2(testInput2) `should be equal to` 103
        }

        test("with test input 3") {
            day12.pt2(testInput3) `should be equal to` 3509
        }

        test("with real input") {
            day12.pt2(realInput) `should be equal to` 117509
        }

    }

})