import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2115: Spek({

    val testInput = readLines("15_test.txt")
    val realInput  = readLines("15_real.txt")

    group("pt1") {

        test("with test input") {
            day15.pt1(testInput) `should be equal to` 40
        }

        test("with real input") {
            day15.pt1(realInput) `should be equal to` 456
        }

    }

    group("pt2") {

        test("with test input") {
            day15.pt2(testInput) `should be equal to` 315
        }

        test("with real input") {
            day15.pt2(realInput) `should be equal to` 2831
        }

    }

})
