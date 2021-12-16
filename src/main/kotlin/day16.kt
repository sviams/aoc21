object day16 {

    data class Packet(val bits: String) {

        val version: Int = bits.take(3).toInt(2)
        val typeId: Int = bits.drop(3).take(3).toInt(2)
        val isLiteral: Boolean = typeId == 4
        fun literalBitChunks(b: String): List<String> = b.chunked(5).takeWhileInclusive { it.first() != '0' }
        fun literalValue(b: String) =  literalBitChunks(b).map { it.drop(1) }.joinToString("").toLong(2)
        val literalValue: Long by lazy { literalValue(bits.drop(6)) }
        val lengthTypeId: Int by lazy { bits.drop(6).take(1).toInt() }
        val subPacketLengthBitCount: Int by lazy { if (this.lengthTypeId == 0) 15 else 11 }
        val subPacketLength: Int by lazy { bits.drop(7).take(subPacketLengthBitCount).toInt(2) }
        val operatorBody: String by lazy { bits.drop(7 + subPacketLengthBitCount) }

        val subPackets: List<Packet> by lazy {
            if (isLiteral) emptyList()
            else {
                val b = if (lengthTypeId == 0) operatorBody.take(subPacketLength) else operatorBody
                val seq = generateSequence(Pair(b, emptyList<Packet>())) { (remaining, packets) ->
                    if (remaining.length < 8) remaining to packets
                    else {
                        val result = Packet(remaining)
                        val newRemaining = remaining.drop(result.length)
                        newRemaining to packets.plus(result)
                    }
                }
                val res = if (lengthTypeId == 0) seq.takeWhileInclusive { it.first.length > 7 } else seq.take(subPacketLength+1)
                res.last().second
            }
        }

        val length: Int by lazy {
            6 + if (isLiteral) literalBitChunks(bits.drop(6)).size * 5 else 1 + subPacketLengthBitCount + subPackets.sumOf { it.length }
        }

        fun sumVersions(): Int = version + subPackets.fold(0) { acc, p -> acc + p.sumVersions() }

        fun value(): Long =
            when (typeId) {
                0 -> subPackets.sumOf { it.value() }
                1 -> subPackets.fold(1) { acc, p -> acc * p.value() }
                2 -> subPackets.minOf { it.value() }
                3 -> subPackets.maxOf { it.value() }
                4 -> literalValue
                5 -> if (subPackets.first().value() > subPackets.last().value()) 1 else 0
                6 -> if (subPackets.first().value() < subPackets.last().value()) 1 else 0
                7 -> if (subPackets.first().value() == subPackets.last().value()) 1 else 0
                else -> -1
            }

        companion object {
            fun fromHex(hex: String): Packet = Packet(hex.map {
                val unpadded = it.digitToInt(16).toString(2)
                generateSequence { "0" }.take(4-unpadded.length).joinToString("") + unpadded
            }.joinToString(""))
        }
    }

    fun pt1(input: String): Int = Packet.fromHex(input).sumVersions()

    fun pt2(input: String): Long = Packet.fromHex(input).value()
}