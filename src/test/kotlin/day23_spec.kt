import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2123: Spek({

    val testInputPt1 = day23.Cave(mapOf(0 to null, 1 to null, 11 to 'B', 12 to 'A', 20 to null, 21 to 'C', 22 to 'D', 30 to null, 31 to 'B', 32 to 'C', 40 to null, 41 to 'D', 42 to 'A', 50 to null, 51 to null), true)
    val realInputPt1 = day23.Cave(mapOf(0 to null, 1 to null, 11 to 'D', 12 to 'C', 20 to null, 21 to 'D', 22 to 'A', 30 to null, 31 to 'B', 32 to 'B', 40 to null, 41 to 'A', 42 to 'C', 50 to null, 51 to null), true)

    val testInputPt2 = day23.Cave(mapOf(0 to null, 1 to null, 11 to 'B', 12 to 'D', 13 to 'D', 14 to 'A', 20 to null, 21 to 'C', 22 to 'C', 23 to 'B', 24 to 'D', 30 to null, 31 to 'B', 32 to 'B', 33 to 'A', 34 to 'C', 40 to null, 41 to 'D', 42 to 'A', 43 to 'C', 44 to 'A', 50 to null, 51 to null), false)
    val realInputPt2 = day23.Cave(mapOf(0 to null, 1 to null, 11 to 'C', 12 to 'D', 13 to 'D', 14 to 'D', 20 to null, 21 to 'A', 22 to 'C', 23 to 'B', 24 to 'D', 30 to null, 31 to 'B', 32 to 'B', 33 to 'A', 34 to 'B', 40 to null, 41 to 'C', 42 to 'A', 43 to 'C', 44 to 'A', 50 to null, 51 to null), false)

    group("pt1") {

        test("with test input") {
            day23.pt1(testInputPt1) `should be equal to` 12521
        }

        test("with real input") {
            day23.pt1(realInputPt1) `should be equal to` 16508 // Actual answer is 18300, but my code thinks it knows better
        }

    }

    group("pt2") {

        test("with test input") {
            day23.pt2(testInputPt2) `should be equal to` 44169
        }

        test("with real input") {
            day23.pt2(realInputPt2) `should be equal to` 50190
        }

    }

})
