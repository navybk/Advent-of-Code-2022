package base

fun Int.result(): Int = this.apply {
    println("result: $this")
}

fun String.result(): String = this.apply {
    println("result: $this")
}