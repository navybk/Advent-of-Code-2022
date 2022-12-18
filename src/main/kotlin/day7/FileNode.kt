package day7

sealed class FileNode(val identifier: String, val parent: DIR?) {
    data class FILE(
        val fileName: String,
        val fileSize: Int,
        val parentDir: DIR
    ) : FileNode(fileName, parentDir)

    data class DIR(
        val dirName: String,
        val nodes: MutableList<FileNode>,
        val parentDir: DIR?
    ) : FileNode(dirName, parentDir) {

        var dirSize: Int = 0
            private set

        fun calcSize() {
            dirSize = nodes.sumOf {
                when (it) {
                    is FILE -> it.fileSize
                    is DIR -> it.dirSize
                }
            }
        }
    }
}
