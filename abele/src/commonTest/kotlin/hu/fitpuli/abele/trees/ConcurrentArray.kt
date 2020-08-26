package hu.fitpuli.abele.trees

expect class ConcurrentArray<T>(initial: Array<T>) : Iterable<T> {
    fun mutate(block: (Array<T>) -> Array<T>)
}