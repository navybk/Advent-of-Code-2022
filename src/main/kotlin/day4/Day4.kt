package day4

import base.FileHandling
import base.expect
import base.result

fun main() {
    Day4()
}

class Day4 {

    init {
        getInput("test.txt").resolveFirst().expect(2)
        getInput("input.txt").resolveFirst()
        getInput("test.txt").resolveSecond().expect(4)
        getInput("input.txt").resolveSecond()
    }

    private fun List<List<Int>>.resolveFirst(): Int = count { ranges ->
        (ranges[0] >= ranges[2] && ranges[1] <= ranges[3]) ||
                (ranges[2] >= ranges[0] && ranges[3] <= ranges[1])
    }.result()

    private fun List<List<Int>>.resolveSecond() = count { ranges ->
        (ranges[0]..ranges[1]).intersect(ranges[2]..ranges[3]).isNotEmpty()
    }.result()

    private fun getInput(filename: String) = FileHandling("/day4/$filename").map { line ->
        line.split(",", "-").map(String::toInt)
    }
}
