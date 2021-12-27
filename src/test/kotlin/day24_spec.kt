import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2124: Spek({

    val realInput  = readLines("24_real.txt")
    
    group("pt1") {

        test("with real input") {
            day24.pt1(realInput) `should be equal to` 39494195799979
        }

    }

    group("pt2") {

        test("with real input") {
            day24.pt2(realInput) `should be equal to` 13161151139617
        }

    }

})
