import java.lang.Math.abs

data class Pos(val x: Int, val y: Int) {
    fun plus(other: Pos) = Pos(x + other.x, y + other.y)
    fun minus(other: Pos) = Pos(x - other.x, y - other.y)
    fun times(other: Pos) = Pos(x * other.x, y * other.y)
    fun distanceTo(other: Pos) = abs(x - other.x) + abs(y - other.y)
    fun product() = x * y
    fun neighbors() = hashSetOf(this.plus(NORTH), this.plus(SOUTH), this.plus(EAST), this.plus(WEST))
    override fun equals(other: Any?): Boolean {
        val o = other as Pos
        return x == o.x && y == o.y
    }

    fun isInFirstQuadrant(): Boolean = x >= 0 && y >= 0

    companion object {
        // Assuming TL is origin
        val NORTH = Pos(0,-1)
        val SOUTH = Pos(0,1)
        val WEST = Pos(-1,0)
        val EAST = Pos(1, 0)
    }
}

fun <T> Sequence<T>.takeWhileInclusive(pred: (T) -> Boolean): Sequence<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = pred(it)
        result
    }
}