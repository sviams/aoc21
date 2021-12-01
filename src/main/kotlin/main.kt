fun main(args: Array<String>) {
    println("Hello $args")
}

tailrec fun<T> List<T>.splitBy(result: List<List<T>> = listOf(), predicate: (T) -> Boolean): List<List<T>> {
    if (this.isEmpty()) return result
    val lines: List<T> = this.takeWhile { predicate(it) }
    return this.drop(lines.size + 1).splitBy(result.plus(listOf(lines)), predicate)
}