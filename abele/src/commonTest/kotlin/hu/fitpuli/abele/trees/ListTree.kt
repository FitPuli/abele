package hu.fitpuli.abele.trees

import hu.fitpuli.abele.Abele
import hu.fitpuli.abele.Tree

class ListTree(allowedLevel: Int = Abele.VERBOSE, allowedTags: Set<String> = setOf()) : Tree() {
    private val allowedTags = allowedTags.toMutableSet()
    private var allowedLevel = allowedLevel
        set(value) {
            when (value) {
                Abele.VERBOSE, Abele.DEBUG, Abele.INFO, Abele.WARNING, Abele.ERROR, Abele.ASSERT -> {
                    field = value
                }
                else -> throw IllegalArgumentException("Unknown log level: $value")
            }
        }

    private val _messages = ConcurrentArray<String>(emptyArray())

    val messages: List<String> get() = _messages.toList()

    override fun isLoggable(priority: Int, tag: String?): Boolean {
        return priority <= allowedLevel && (allowedTags.isEmpty() || allowedTags.contains(tag))
    }

    override fun performLog(priority: Int, tag: String?, throwable: Throwable?, message: String?) {
        if (isLoggable(priority, tag)) {
            _messages.mutate {
                it + buildString {
                    append(priority.toPriorityString())
                    if (tag != null) {
                        append(' ')
                        append(tag)
                    }
                    if (message != null) {
                        append(' ')
                        append(message)
                    }
                    if (throwable != null) {
                        append(" [")
                        append(throwable::class)
                        append(": ")
                        append(throwable.message)
                        append(']')
                    }
                }
            }
        }
    }

    private fun Int.toPriorityString() = when (this) {
        Abele.VERBOSE -> "VERBOSE"
        Abele.DEBUG -> "DEBUG"
        Abele.INFO -> "INFO"
        Abele.WARNING -> "WARNING"
        Abele.ERROR -> "ERROR"
        Abele.ASSERT -> "ASSERT"
        else -> throw IllegalArgumentException("Unknown priority: $this")
    }
}
