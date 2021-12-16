import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2116: Spek({

    val testInput = readLines("16_test.txt")
    val realInput  = readLines("16_real.txt")

    group("pt1") {

        test("bits conversion works") {
            day16.Packet.fromHex("D2FE28").bits `should be equal to` "110100101111111000101000"
        }

        test("bits conversion works 2") {
            day16.Packet.fromHex("EE00D40C823060").bits `should be equal to` "11101110000000001101010000001100100000100011000001100000"
        }

        test("version works") {
            day16.Packet.fromHex("D2FE28").version `should be equal to` 6
        }

        test("type ID works") {
            day16.Packet.fromHex("D2FE28").typeId `should be equal to` 4
        }

        test("literal value works") {
            day16.Packet.fromHex("D2FE28").literalValue `should be equal to` 2021
        }

        test("length works for literal") {
            day16.Packet.fromHex("D2FE28").length `should be equal to` "110100101111111000101".length
        }

        test("lengthTypeId works") {
            day16.Packet.fromHex("38006F45291200").lengthTypeId `should be equal to` 0
        }

        test("subPackets for type 0 works") {
            day16.Packet.fromHex("38006F45291200").subPackets.map { it.literalValue } `should be equal to` listOf(10,20)
        }

        test("subPackets for type 1 works") {
            day16.Packet.fromHex("EE00D40C823060").subPackets.map { it.literalValue } `should be equal to` listOf(1,2,3)
        }

        test("with test input") {
            day16.pt1("8A004A801A8002F478") `should be equal to` 16
        }

        test("with test input 2") {
            day16.pt1("620080001611562C8802118E34") `should be equal to` 12
        }

        test("with test input 3") {
            day16.pt1("C0015000016115A2E0802F182340") `should be equal to` 23
        }

        test("with test input 4") {
            day16.pt1("A0016C880162017C3686B18A3D4780") `should be equal to` 31
        }

        test("with real input") {
            day16.pt1(realInput.first()) `should be equal to` 925
        }

    }

    group("pt2") {

        test("with test input") {
            day16.pt2("C200B40A82") `should be equal to` 3
        }

        test("with test input 2") {
            day16.pt2("04005AC33890") `should be equal to` 54
        }

        test("with test input 3") {
            day16.pt2("880086C3E88112") `should be equal to` 7
        }

        test("with test input 4") {
            day16.pt2("CE00C43D881120") `should be equal to` 9
        }

        test("with test input 5") {
            day16.pt2("D8005AC2A8F0") `should be equal to` 1
        }

        test("with test input 6") {
            day16.pt2("F600BC2D8F") `should be equal to` 0
        }

        test("with test input 7") {
            day16.pt2("9C005AC2F8F0") `should be equal to` 0
        }

        test("with test input 8") {
            day16.pt2("9C0141080250320F1802104A08") `should be equal to` 1
        }

        test("with real input") {
            day16.pt2(realInput.first()) `should be equal to` 342997120375
        }

    }

})
