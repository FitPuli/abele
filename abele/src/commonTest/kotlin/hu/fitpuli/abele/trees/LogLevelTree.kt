package hu.fitpuli.abele.trees

import hu.fitpuli.abele.Tree

class LogLevelTree : Tree() {
    private val _logs = ConcurrentArray<Int>(emptyArray())

    val logs: List<Int> get() = _logs.toList()

    override fun isLoggable(priority: Int, tag: String?): Boolean = true

    override fun performLog(priority: Int, tag: String?, throwable: Throwable?, message: String?) {
        if (isLoggable(priority, tag)) {
            _logs.mutate { it + priority }
        }
    }
}
