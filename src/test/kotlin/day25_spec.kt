import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2125: Spek({

    val realInput  = readLines("25_real.txt")
    val testInput  = readLines("25_test.txt")
    
    group("pt1") {

        test("with test input") {
            day25.pt1(testInput) `should be equal to` 58
        }
        
        test("with real input") {
            day25.pt1(realInput) `should be equal to` 426
        }

    }

})
