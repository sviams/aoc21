import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2108: Spek({

    val testInput = readLines("8_test.txt")
    val realInput  = readLines("8_real.txt")

    group("pt1") {

        test("with test input") {
            day8.pt1(testInput) `should be equal to` 26
        }

        test("with real input") {
            day8.pt1(realInput) `should be equal to` 261
        }

    }

    group("pt2") {

        test("with test input 1") {
            day8.pt2(listOf("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")) `should be equal to` 5353
        }

        test("with test input 2") {
            day8.pt2(listOf("be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe")) `should be equal to` 8394
        }

        test("with test input 3") {
            day8.pt2(listOf("edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc")) `should be equal to` 9781
        }

        test("with test input 4") {
            day8.pt2(listOf("fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg")) `should be equal to` 1197
        }

        test("with test input 5") {
            day8.pt2(listOf("fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb")) `should be equal to` 9361
        }

        test("with test input 6") {
            day8.pt2(listOf("aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea")) `should be equal to` 4873
        }

        test("with test input 7") {
            day8.pt2(listOf("cd gfcde cdbfage cbdfae edc fedgac gadc feagd febdga gbfec | adbfeg dbfcae facbde gcfde")) `should be equal to` 6003
        }

        test("with test input") {
            day8.pt2(testInput) `should be equal to` 61229
        }

        test("with real input") {
            day8.pt2(realInput) `should be equal to` 987553
        }

    }

})