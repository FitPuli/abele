package hu.fitpuli.abele.trees

import kotlin.native.concurrent.AtomicReference
import kotlin.native.concurrent.freeze

actual class ConcurrentArray<T> actual constructor(initial: Array<T>) : Iterable<T> {
    private val reference = AtomicReference(initial)

    actual fun mutate(block: (Array<T>) -> Array<T>) {
        do {
            val array = reference.value
            val updated = block(array).freeze()
        } while (!reference.compareAndSet(array, updated))
    }

    override fun iterator(): Iterator<T> = reference.value.copyOf().iterator()
}
