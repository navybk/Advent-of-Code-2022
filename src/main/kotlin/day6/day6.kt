package day6

import base.FileHandling
import base.expect
import base.result

fun main() {
    Day6()
}

class Day6 {

    init {
        getInput("test.txt").apply {
            get(0).resolveFirst().expect(7)
            get(1).resolveFirst().expect(5)
            get(2).resolveFirst().expect(6)
            get(3).resolveFirst().expect(10)
            get(4).resolveFirst().expect(11)
        }
        getInput("input.txt")[0].resolveFirst()

        getInput("test.txt").apply {
            get(0).resolveSecond().expect(19)
            get(1).resolveSecond().expect(23)
            get(2).resolveSecond().expect(23)
            get(3).resolveSecond().expect(29)
            get(4).resolveSecond().expect(26)
        }

        getInput("input.txt")[0].resolveSecond()
    }

    private fun String.resolveFirst(): Int = parseSequence(4).result()

    private fun String.resolveSecond(): Int = parseSequence(14).result()

    private fun String.parseSequence(windowsSize: Int) =
        slidingWindow(windowsSize) {
            val map = mutableMapOf<Char, Boolean>()
            it.forEach { character ->
                map[character] = map[character]?.apply {
                    return@slidingWindow false
                } ?: true
            }
            true
        } + windowsSize

    private fun String.slidingWindow(size: Int, predicate: (String) -> Boolean): Int =
        asSequence().forEachIndexed { index, _ ->
            substring(
                startIndex = index,
                endIndex = (index + size).coerceAtMost(length)
            ).let {
                if (predicate(it)) {
                    return index
                }
            }
        }.let {
            -1
        }

    private fun getInput(filename: String) = FileHandling("/day6/$filename").getStringList()
}
