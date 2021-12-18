import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2118: Spek({

    val testInput = readLines("18_test.txt")
    val testInput2 = readLines("18_test2.txt")
    val realInput  = readLines("18_real.txt")

    group("pt1") {

        test("explode works 1") {
            day18.explode("[[[[[9,8],1],2],3],4]") `should be equal to` "[[[[0,9],2],3],4]"
        }

        test("explode works 2") {
            day18.explode("[7,[6,[5,[4,[3,2]]]]]") `should be equal to` "[7,[6,[5,[7,0]]]]"
        }

        test("explode works 3") {
            day18.explode("[[6,[5,[4,[3,2]]]],1]") `should be equal to` "[[6,[5,[7,0]]],3]"
        }

        test("explode works 4") {
            day18.explode("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]") `should be equal to` "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"
        }

        test("explode works 5") {
            day18.explode("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]") `should be equal to` "[[3,[2,[8,0]]],[9,[5,[7,0]]]]"
        }

        test("explode works 6") {
            day18.explode("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]") `should be equal to` "[[[[0,7],4],[7,[[8,4],9]]],[1,1]]"
        }

        test("explode works 7") {
            day18.explode("[[[[0,7],4],[7,[[8,4],9]]],[1,1]]") `should be equal to` "[[[[0,7],4],[15,[0,13]]],[1,1]]"
        }

        test("explode works 8") {
            day18.explode("[[[[[6,7],0],[6,7]],[[5,10],[26,0]]],[[2,[11,10]],[[0,8],[8,0]]]]") `should be equal to` "[[[[0,7],[6,7]],[[5,10],[26,0]]],[[2,[11,10]],[[0,8],[8,0]]]]"
        }

        test("explode works 9") {
            day18.explode("[[[[6,7],[0,7]],[[7,[6,7]],[0,21]]],[[2,[11,10]],[[0,8],[8,0]]]]") `should be equal to` "[[[[6,7],[0,7]],[[13,0],[7,21]]],[[2,[11,10]],[[0,8],[8,0]]]]"
        }

        test("explode works 10") {
            day18.explode("[[[[7,7],[7,7]],[[7,8],[7,0]]],[[[8,8],[7,[7,8]]],[[21,10],[5,0]]]]") `should be equal to` "[[[[7,7],[7,7]],[[7,8],[7,0]]],[[[8,8],[14,0]],[[29,10],[5,0]]]]"
        }

        test("explode works 11") {
            day18.explode("[[[[12,12],[6,14]],[[15,0],[17,[8,1]]]],[2,9]]") `should be equal to` "[[[[12,12],[6,14]],[[15,0],[25,0]]],[3,9]]"
        }

        // [[[[7,7],[0,7]],[[8,7],[[17,17]7,0]]],[3,9]]
        test("split works 1") {
            day18.split("[12,1]") `should be equal to` "[[6,6],1]"
        }

        test("split works 2") {
            day18.split("[13,1]") `should be equal to` "[[6,7],1]"
        }

        test("split works 3") {
            day18.split("[1,13]") `should be equal to` "[1,[6,7]]"
        }

        test("reduce works") {
            day18.reduce("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]") `should be equal to` "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"
        }

        test("reduce works 2") {
            day18.reduce(day18.add("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]", "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]")) `should be equal to` "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]"
        }

        test("reduce works 3") {
            day18.reduce(day18.add("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]", "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]")) `should be equal to` "[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]"
        }

        test("reduce works 4") {
            day18.reduce(day18.add("[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]", "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]")) `should be equal to` "[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]"
        }

        test("reduce works 5") {
            day18.reduce(day18.add("[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]", "[7,[5,[[3,8],[1,4]]]]")) `should be equal to` "[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]"
        }

        test("reduce works 6") {
            day18.reduce(day18.add("[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]", "[[2,[2,2]],[8,[8,1]]]")) `should be equal to` "[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]"
        }

        test("reduce works 7") {
            day18.reduce(day18.add("[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]", "[2,9]")) `should be equal to` "[[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]"
        }

        test("addList works") {
            day18.addList(testInput) `should be equal to` "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"
        }

        test("magnitude works") {
            day18.calcMagnitude("[[1,2],[[3,4],5]]") `should be equal to` 143
        }

        test("magnitude works 2") {
            day18.calcMagnitude("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]") `should be equal to` 1384
        }

        test("magnitude works 3") {
            day18.calcMagnitude("[[[[1,1],[2,2]],[3,3]],[4,4]]") `should be equal to` 445
        }

        test("magnitude works 4") {
            day18.calcMagnitude("[[[[3,0],[5,3]],[4,4]],[5,5]]") `should be equal to` 791
        }

        test("magnitude works 5") {
            day18.calcMagnitude("[[[[5,0],[7,4]],[5,5]],[6,6]]") `should be equal to` 1137
        }

        test("magnitude works 6") {
            day18.calcMagnitude("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]") `should be equal to` 3488
        }

        test("with test input") {
            day18.pt1(testInput2) `should be equal to` 4140
        }

        test("with real input") {
            day18.pt1(realInput) `should be equal to` 4469
        }

    }

    group("pt2") {

        test("with test input") {
            day18.pt2(testInput2) `should be equal to` 3993
        }

        test("with test input 2") {
            day18.pt2(listOf("[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]", "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]")) `should be equal to` 3993
        }

        test("with real input") {
            day18.pt2(realInput) `should be equal to` 4770
        }

    }

})
