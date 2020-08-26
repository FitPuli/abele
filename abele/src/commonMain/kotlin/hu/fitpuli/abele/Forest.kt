package hu.fitpuli.abele

internal expect class Forest(trees: Array<Tree>) {
    val trees: Array<Tree>

    fun update(block: (Array<Tree>) -> Array<Tree>)
}
