import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2114: Spek({

    val testInput = readLines("14_test.txt")
    val realInput  = readLines("14_real.txt")

    group("pt1") {

        test("with test input") {
            day14.pt1(testInput) `should be equal to` 1588
        }

        test("with real input") {
            day14.pt1(realInput) `should be equal to` 2408
        }

    }

    group("pt2") {

        test("with test input") {
            day14.pt2(testInput) `should be equal to` 2188189693529
        }

        test("with real input") {
            day14.pt2(realInput) `should be equal to` 2651311098752
        }

    }

})
