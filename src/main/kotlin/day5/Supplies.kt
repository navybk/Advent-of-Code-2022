package day5

enum class Action {
    MOVE
}

data class Rule(
    val action: Action,
    val amount: Int,
    val origin: Int,
    val destination: Int
)

typealias CrateStack = MutableList<Char>

data class Supplies(
    val stacks: List<CrateStack>,
    val rules: List<Rule>
)
