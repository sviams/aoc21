import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2122: Spek({

    val testInput = readLines("22_test.txt")
    val testInput2 = readLines("22_test2.txt")
    val realInput  = readLines("22_real.txt")

    group("pt1") {

        test("with test input") {
            day22.pt1(testInput) `should be equal to` 590784
        }

        test("with real input") {
            day22.pt1(realInput) `should be equal to` 612714
        }

    }

    group("pt2") {

        test("with test input") {
            day22.pt2(testInput2) `should be equal to` 2758514936282235
        }

        test("with real input") {
            day22.pt2(realInput) `should be equal to` 1311612259117092
        }

    }

})
