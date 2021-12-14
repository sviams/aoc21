import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2114: Spek({

    val testInput = readLines("14_test.txt")
    val realInput  = readLines("14_real.txt")
    val perInput  = readLines("14_per.txt")

    group("pt1") {

        test("with test input") {
            day14.pt1(testInput) `should be equal to` 1588
        }

        test("with Pers input") {
            day14.pt1(perInput) `should be equal to` 2988
        }

        test("with real input") {
            day14.pt1(realInput) `should be equal to` 2408
        }

    }

    group("pt2") {

        test("with test input") {
            day14.pt2(testInput) `should be equal to` 2188189693529
        }

        test("with Pers input") {
            day14.pt2(perInput) `should be equal to` 3572761917024
        }

        test("with real input") {
            day14.pt2(realInput) `should be equal to` 2651311098752
        }

    }

})
