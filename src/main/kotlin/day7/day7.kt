package day7

import base.FileHandling
import base.result
import day7.FileNode.DIR
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

fun main() {
    Day7()
}

class Day7 {

    init {
        getInput("test.txt").resolveFirst()
        getInput("input.txt").resolveFirst()

        getInput("test.txt").resolveSecond()
        getInput("input.txt").resolveSecond()
    }

    private fun List<DIR>.resolveFirst() = this.sumOf {
        if (it.dirSize <= 100000) {
            it.dirSize
        } else {
            0
        }
    }.result()

    private fun List<DIR>.resolveSecond(): Int {
        val size = first().dirSize
        val minSize = 30000000 - (70000000 - size)
        return minOf {
            if (it.dirSize >= minSize) {
                it.dirSize
            } else {
                size
            }
        }.result()
    }

    private fun getInput(filename: String) = FileHandling("/day7/$filename").let {
        val tempDirList = mutableListOf(DIR("/", mutableListOf(), null))
        var currentNode: DIR = tempDirList[0]
        it.iterate { line ->
            if (line[0] == '$') {
                currentNode = tempDirList.getLevel(line.parseCommand(), currentNode)
            } else {
                val node = line.parseFileNode(currentNode)
                tempDirList.addNode(currentNode, node)
            }
        }
        tempDirList.apply {
            reversed().onEach(DIR::calcSize)
        }
    }

    private fun List<DIR>.getLevel(command: Command, startLevel: DIR): DIR {
        if (command !is Command.CD) {
            return startLevel
        }

        return when (command.target) {
            "/" -> get(0)
            ".." -> startLevel.parent ?: get(0)
            else -> startLevel.nodes.find {
                it is DIR && it.dirName == command.target
            } as? DIR ?: throw IllegalStateException("Dir ${command.target} not found")
        }
    }

    private fun MutableList<DIR>.addNode(currentNode: DIR, fileNode: FileNode) {
        when (fileNode) {
            is FileNode.FILE -> currentNode.nodes.add(fileNode)
            is DIR -> currentNode.nodes.find {
                it is DIR && fileNode.dirName == it.dirName
            }?.apply {
                throw IllegalStateException("node ${fileNode.dirName} exists")
            } ?: apply {
                currentNode.nodes.add(fileNode)
                add(fileNode)
            }
        }
    }

    private fun String.parseCommand(): Command {
        val substrings = split(" ")
        return when (substrings[1]) {
            "ls" -> Command.LS
            "cd" -> Command.CD(substrings[2])
            else -> throw IllegalArgumentException("unknown command ${substrings[1]}")
        }
    }

    private fun String.parseFileNode(parent: DIR): FileNode {
        val substrings = split(" ")
        val name = substrings[1]
        return if (substrings[0] == "dir") {
            DIR(
                dirName = name,
                nodes = mutableListOf(),
                parentDir = parent
            )
        } else {
            FileNode.FILE(
                fileName = name,
                fileSize = substrings[0].toInt(),
                parentDir = parent
            )
        }
    }
}
