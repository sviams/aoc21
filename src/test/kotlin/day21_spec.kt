import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2121: Spek({

    group("pt1") {

        test("with test input") {
            day21.pt1(4,8) `should be equal to` 739785
        }

        test("with real input") {
            day21.pt1(10,9) `should be equal to` 918081
        }

    }

    group("pt2") {

        test("with test input") {
            day21.pt2(4,8) `should be equal to` 444356092776315
        }

        test("with real input") {
            day21.pt2(10,9) `should be equal to` 158631174219251
        }

    }

})
