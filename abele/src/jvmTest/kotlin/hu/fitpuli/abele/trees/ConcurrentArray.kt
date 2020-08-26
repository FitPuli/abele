package hu.fitpuli.abele.trees

actual class ConcurrentArray<T> actual constructor(initial: Array<T>) : Iterable<T> {
    private var internalArray = initial

    actual fun mutate(block: (Array<T>) -> Array<T>) = synchronized(this) {
        internalArray = block(internalArray)
    }

    override fun iterator(): Iterator<T> {
        return internalArray.copyOf().iterator()
    }
}