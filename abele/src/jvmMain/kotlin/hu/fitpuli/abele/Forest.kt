package hu.fitpuli.abele

internal actual class Forest actual constructor(trees: Array<Tree>) {
    private var internalTrees: Array<Tree> = trees

    actual val trees: Array<Tree>
        get() = internalTrees

    actual fun update(block: (Array<Tree>) -> Array<Tree>) = synchronized(this) {
        internalTrees = block(internalTrees)
    }
}
