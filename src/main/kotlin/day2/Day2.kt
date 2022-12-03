package day2

import base.FileHandling
import base.expect

fun main() {
    Day2()
}

class Day2 {

    init {
        getInput("test.txt").resolveFirst().expect(15)
        getInput("input.txt").resolveFirst()
        getInput("test.txt").resolveSecond().expect(12)
        getInput("input.txt").resolveSecond()
    }

    private fun List<Pair<Rps, Char>>.resolveFirst() = sumOf { (first, second) ->
        val assumption = second.toNum()
        when {
            first == assumption -> first.value + RpsResult.Draw.score
            first.losesAgainst == assumption -> assumption.value + RpsResult.Win.score
            else -> assumption.value
        }
    }.apply {
        println("result: $this")
    }

    private fun List<Pair<Rps, Char>>.resolveSecond() = sumOf { (first, second) ->
        when (second.toWinStategy()) {
            RpsResult.Win -> 6 + first.losesAgainst.value
            RpsResult.Lose -> first.winAgainst.value
            else -> first.value + 3
        }
    }.apply {
        println("result: $this")
    }

    private fun getInput(filename: String) = FileHandling("/day2/$filename").map {
        it[0].toNum() to it[2]
    }

    private fun Char.toNum(): Rps =
        when (this) {
            'A', 'X' -> Rps.Rock
            'B', 'Y' -> Rps.Paper
            'C', 'Z' -> Rps.Scissors
            else -> error("Nope")
        }

    private fun Char.toWinStategy(): RpsResult =
        when (this) {
            'X' -> RpsResult.Lose
            'Y' -> RpsResult.Draw
            'Z' -> RpsResult.Win
            else -> error("Nope")
        }

    sealed class Rps(
        val value: Int,
    ) {
        object Scissors : Rps(3)
        object Paper : Rps(2)
        object Rock : Rps(1)

        val losesAgainst
            get() = when (this) {
                Scissors -> Rock
                Rock -> Paper
                Paper -> Scissors
            }

        val winAgainst
            get() = when (this) {
                Scissors -> Paper
                Rock -> Scissors
                Paper -> Rock
            }
    }

    sealed class RpsResult(val score: Int) {
        object Win : RpsResult(6)
        object Lose : RpsResult(0)
        object Draw : RpsResult(3)
    }
}
