import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2113: Spek({

    val testInput = readLines("13_test.txt")
    val realInput  = readLines("13_real.txt")

    group("pt1") {

        test("with test input") {
            day13.pt1(testInput) `should be equal to` 17
        }

        test("with real input") {
            day13.pt1(realInput) `should be equal to` 661
        }

    }

    group("pt2") {

        test("with test input") {
            day13.pt2(testInput) `should be equal to` -1
        }

        test("with real input") {
            day13.pt2(realInput) `should be equal to` -1 // PFKLKCFP
        }

    }

})
