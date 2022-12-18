package day7

sealed class Command {
    data class CD(val target: String) : Command()
    object LS : Command()
}
