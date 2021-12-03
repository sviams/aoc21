object day3 {

    fun simpleDiagnostic(input: List<String>, compFunc: (a: Int, b: Int) -> Boolean): Int = input.first().indices.fold("") { acc, index ->
        acc + if (compFunc(input.map { it[index] }.count { it == '1' }, input.size/2)) '1' else '0'
    }.toInt(2)

    tailrec fun diagnostic(input: List<String>, index: Int = 0, compFunc: (a: Float, b: Float) -> Boolean): Int =
        if (input.size == 1) input.first().toInt(2)
        else diagnostic(input.filter {
            if (compFunc(input.map { line -> line[index] }.count { c -> c == '1' }.toFloat(), input.size.toFloat()/2.0f)) it[index] == '1'
            else it[index] == '0' }, index + 1, compFunc)

    fun pt1(input: List<String>): Int =
        simpleDiagnostic(input) {a,b -> a > b} * simpleDiagnostic(input) {a,b -> a < b}

    fun pt2(input: List<String>): Int =
        diagnostic(input) {a,b -> a >= b} * diagnostic(input) {a,b -> a < b}

}