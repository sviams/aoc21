import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2120: Spek({

    val testInput = readLines("20_test.txt")
    val realInput  = readLines("20_real.txt")

    group("pt1") {

        test("with test input") {
            day20.pt1(testInput) `should be equal to` 35
        }

        test("with real input") {
            day20.pt1(realInput) `should be equal to` 5391
        }

    }

    group("pt2") {

        test("with test input") {
            day20.pt2(testInput) `should be equal to` 3351
        }

        test("with real input") {
            day20.pt2(realInput) `should be equal to` 16383
        }

    }

})
