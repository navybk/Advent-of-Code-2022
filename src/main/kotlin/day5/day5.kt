package day5

import base.FileHandling
import base.expect
import base.result

fun main() {
    Day5()
}

class Day5 {

    init {
        getInput("test.txt").resolveFirst().expect("CMZ")
        getInput("input.txt").resolveFirst()
        getInput("test.txt").resolveSecond().expect("MCD")
        getInput("input.txt").resolveSecond()
    }

    private fun Supplies.resolveFirst(): String {
        rules.asSequence().forEach { rule ->
            repeat(rule.amount) {
                stacks[rule.destination].add(0, stacks[rule.origin].removeFirst())
            }
        }
        return stacks.map {
            it.first()
        }.joinToString("").result()
    }

    private fun Supplies.resolveSecond(): String {
        rules.asSequence().forEach { rule ->
            val tempList = mutableListOf<Char>()
            repeat(rule.amount) {
                tempList.add(stacks[rule.origin].removeFirst())
            }
            stacks[rule.destination].addAll(0, tempList)
        }
        return stacks.map {
            it.first()
        }.joinToString("")
            .result()
    }

    private fun getInput(filename: String): Supplies {
        val stacks = mutableMapOf<Int, CrateStack>()
        val rules = mutableListOf<Rule>()
        var ruleSplit = false
        FileHandling("/day5/$filename").iterate {
            if (it.isEmpty() || it[1] == '1') {
                ruleSplit = true
                return@iterate
            }
            if (ruleSplit) {
                val rule = it.split(" ")
                rules.add(
                    Rule(
                        action = Action.valueOf(rule[0].uppercase()),
                        amount = rule[1].toInt(),
                        origin = rule[3].toInt() - 1,
                        destination = rule[5].toInt() - 1
                    )
                )
            } else {
                it.chunked(4).forEachIndexed { index, s ->
                    if (s.isBlank()) {
                        return@forEachIndexed
                    }
                    stacks[index] = stacks[index] ?: mutableListOf()
                    stacks[index]!!.add(s[1])
                }
            }
        }
        return Supplies(
            stacks = stacks.toSortedMap().values.toList(),
            rules = rules
        )
    }
}
