package base

class FileHandling(private val filename: String) {

    fun <T> useLines(block: (Sequence<String>) -> T): T =
        bufferedReader
            .useLines(block)

    fun iterate(function: (String) -> Unit) {
        val stream = bufferedReader
        var line = stream.readLine()
        while (line != null) {
            function(line)
            line = stream.readLine()
        }
        bufferedReader.close()
    }

    fun <T : Any> map(translate: (String) -> T?): List<T> =
        getStringList()
            .asSequence()
            .mapNotNull {
                translate(it.trim())
            }
            .toList()

    fun <T : Any> mapTo(seperator: String? = null, translate: (String) -> T?): Map<Int, List<T>> =
        mutableMapOf<Int, List<T>>().apply {
            var start = 0
            val list = mutableListOf<T>()
            getStringList()
                .asSequence()
                .forEach {
                    if (it == seperator || it.isEmpty()) {
                        this[start] = list.toList()
                        list.clear()
                        start++
                    } else {
                        translate(it.trim())
                            ?.apply {
                                list.add(this)
                            }
                    }
                }
            this[start] = list.toList()
        }

    fun getStringList(): List<String> =
        bufferedReader.run {
            readLines().also {
                close()
            }
        }

    private val bufferedReader
        get() = FileHandling::class.java
            .getResourceAsStream(filename)!!
            .bufferedReader()
}
