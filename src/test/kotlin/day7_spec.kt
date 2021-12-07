import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2107: Spek({

    val testInput = readLines("7_test.txt")
    val realInput  = readLines("7_real.txt")

    group("pt1") {

        test("with test input") {
            day7.pt1(testInput) `should be equal to` 37
        }

        test("with real input") {
            day7.pt1(realInput) `should be equal to` 336131
        }

    }

    group("pt2") {

        test("with test input") {
            day7.pt2(testInput) `should be equal to` 168
        }

        test("with real input") {
            day7.pt2(realInput) `should be equal to` 92676646
        }

    }

})