package day3

import base.FileHandling
import base.expect
import base.result

fun main() {
    Day3()
}

class Day3 {

    init {
        getInput("test.txt").resolveFirst().expect(157)
        getInput("input.txt").resolveFirst()
        getInput("test.txt").resolveSecond().expect(70)
        getInput("input.txt").resolveSecond()
    }

    private fun List<List<Char>>.resolveFirst() = sumOf { line ->
        val (first, second) = line.chunked(line.size / 2)
            .let {
                it[0] to it[1]
            }
        first.intersect(second)
            .sumOf {
                chars.indexOf(it) + 1
            }
    }.result()

    private fun List<List<Char>>.resolveSecond() = windowed(3, 3, false).sumOf { compartment ->
        compartment[0].intersect(compartment[1])
            .intersect(compartment[2])
            .sumOf {
                chars.indexOf(it) + 1
            }
    }.result()

    private fun getInput(filename: String) = FileHandling("/day3/$filename").map {
        it.toList()
    }

    companion object {
        val chars = ('a'..'z') + ('A'..'Z')
    }
}
