object day19 {

    data class Scanner(val beacons: List<Pos3D>, val scanners: List<Pos3D>, val ordinal: Int) {
        fun rotate(rotFunc: (Pos3D) -> Pos3D): Scanner = Scanner(beacons.map(rotFunc), scanners, ordinal)
        fun merge(o: Scanner, scanPos: Pos3D): Scanner = Scanner(beacons.plus(o.beacons).distinct(), scanners.plus(scanPos), ordinal)
        fun offset(d: Pos3D): Scanner = Scanner(beacons.map { it + d }, scanners, ordinal)
        override fun equals(other: Any?): Boolean = (other as Scanner).ordinal == ordinal

        fun matchingBeaconsByDistance(other: Scanner): List<Pair<Pos3D, Pos3D>> = distances.fold(emptyList()) { acc, (b, dists) ->
            val bestMatch = other.distances.firstOrNull { (o, odists) ->  odists.intersect(dists).size >= 9 }?.first // Test input won't work with 12
            if (bestMatch != null) acc.plus(b to bestMatch) else acc
        }

        val distances: List<Pair<Pos3D, List<Int>>> by lazy {
            beacons.map { b -> b to beacons.minus(b).map { other -> b.distanceTo(other) } }
        }
    }

    fun parseInput(input: List<String>): List<Scanner> =
        input.splitBy { it.isNotEmpty() }.map { chunk ->
            val (ord) = """scanner (\d+) """.toRegex().find(chunk.first())!!.destructured
            val beacons = chunk.drop(1).map { line ->
                val (x,y,z) = line.split(",").map { it.toInt() }
                Pos3D(x,y,z)
            }
            Scanner(beacons, listOf(Pos3D(0,0,0)), ord.toInt())
        }

    tailrec fun reduce(todo: List<Scanner>, world: Scanner, ): Scanner {
        if (todo.isEmpty()) return world
        val (newWorld, removed) = foldWithRestIfPossible(world, todo)
        return reduce(todo.minus(removed), newWorld)
    }

    fun foldWithRestIfPossible(s: Scanner, rest: List<Scanner>): Pair<Scanner, List<Scanner>> =
        rest.fold(s to emptyList()) { (world, removed), scanner ->
            val matchingBeaconsByDistance = world.matchingBeaconsByDistance(scanner)
            if (matchingBeaconsByDistance.isEmpty()) world to removed else {
                val possibleOffsets = matchingBeaconsByDistance.flatMap { b -> Pos3D.rotations().map { rot -> b.first - rot(b.second) } }
                val rotationAndOffset = possibleOffsets.fold(emptyList<Pair<Pair<(Pos3D) -> Pos3D, Pos3D>, Int>>()) { acc, possibleOffset ->
                    val bestIntersectionsByRotationAndOffset = Pos3D.rotations().map { possibleRotation ->
                        Pair(possibleRotation, possibleOffset) to scanner.rotate(possibleRotation).offset(possibleOffset).beacons.intersect(world.beacons).size
                    }.maxByOrNull { it.second }
                    if (bestIntersectionsByRotationAndOffset != null) acc + bestIntersectionsByRotationAndOffset else acc
                }.filter { it.second > 1 }.maxByOrNull { it.second }?.first!!
                val correctlyOffset = scanner.rotate(rotationAndOffset.first).offset(rotationAndOffset.second)
                world.merge(correctlyOffset, rotationAndOffset.second) to removed.plus(correctlyOffset)
            }
        }

    fun pt1(input: List<String>): Int {
        val scanners = parseInput(input)
        return reduce(scanners.drop(1), scanners.first()).beacons.size
    }

    fun pt2(input: List<String>): Int {
        val scanners = parseInput(input)
        val world: Scanner = reduce(scanners.drop(1), scanners.first())
        return world.scanners.fold(0) { best, s ->
            val bestForScanner = world.scanners.minus(s).map { s.distanceTo(it) }.maxOrNull() ?: -1
            if (bestForScanner > best) bestForScanner else best
        }
    }
}