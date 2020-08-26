package hu.fitpuli.abele

import kotlin.native.concurrent.AtomicReference
import kotlin.native.concurrent.freeze

internal actual class Forest actual constructor(trees: Array<Tree>) {
    private val reference = AtomicReference(trees.freeze())

    actual val trees: Array<Tree>
        get() = reference.value

    actual fun update(block: (Array<Tree>) -> Array<Tree>) {
        do {
            val trees = reference.value
            val updated = block(trees).freeze()
        } while (!reference.compareAndSet(trees, updated))
    }
}
