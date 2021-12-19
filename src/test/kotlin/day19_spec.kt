import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2119: Spek({

    val testInput = readLines("19_test.txt")
    val realInput  = readLines("19_real.txt")

    group("pt1") {

        test("with test input") {
            day19.pt1(testInput) `should be equal to` 79
        }

        test("with real input") {
            day19.pt1(realInput) `should be equal to` 462
        }

    }

    group("pt2") {

        test("with test input") {
            day19.pt2(testInput) `should be equal to` 3621
        }

        test("with real input") {
            day19.pt2(realInput) `should be equal to` 12158
        }

    }

})
