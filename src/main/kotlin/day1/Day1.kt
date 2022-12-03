package day1

import base.FileHandling
import base.expect

fun main() {
    Day1()
}

class Day1 {

    init {
        getInput("test.txt").resolveFirst().expect(24000)
        getInput("input.txt").resolveFirst()
        getInput("test.txt").resolveSecond().expect(45000)
        getInput("input.txt").resolveSecond()

    }

    private fun Map<Int, List<Int>>.resolveFirst() = maxOf {
        it.value.sum()
    }.apply {
        println("result: $this")
    }

    private fun Map<Int, List<Int>>.resolveSecond() = map {
        it.value.sum()
    }.sortedDescending()
        .subList(0, 3)
        .sum()
        .apply {
            println("result: $this")
        }


    private fun getInput(filename: String) = FileHandling("/day1/$filename").mapTo {
        it.toIntOrNull()
    }
}
