import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2117: Spek({

    group("pt1") {

        test("with test input 4") {
            day17.pt1("target area: x=20..30, y=-10..-5") `should be equal to` 45
        }

        test("with real input") {
            day17.pt1("target area: x=155..215, y=-132..-72") `should be equal to` 8646
        }

    }

    group("pt2") {

        test("with test input") {
            day17.pt2("target area: x=20..30, y=-10..-5") `should be equal to` 112
        }


        test("with real input") {
            day17.pt2("target area: x=155..215, y=-132..-72") `should be equal to` 5945
        }

    }

})
