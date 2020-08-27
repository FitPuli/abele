package hu.fitpuli.abele

/**
 * Keeps references for the configured [Tree] instances.
 */
internal expect class Forest(trees: Array<Tree>) {
    /**
     * Gets the stored [Tree] instances.
     */
    val trees: Array<Tree>

    /**
     * Updates the stored [Tree] array. Should be thread safe!
     */
    fun update(block: (Array<Tree>) -> Array<Tree>)
}
