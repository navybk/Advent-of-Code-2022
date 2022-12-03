package base

fun Int.result(): Int = this.apply {
    println("result: $this")
}