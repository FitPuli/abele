package hu.fitpuli.abele.trees

import hu.fitpuli.abele.Tree

sealed class DummyTree(private val isEnabled: Boolean) : Tree() {
    override fun isLoggable(priority: Int, tag: String?): Boolean = isEnabled
    override fun performLog(priority: Int, tag: String?, throwable: Throwable?, message: String?) = Unit

    class Enabled : DummyTree(isEnabled = true)
    class Disabled : DummyTree(isEnabled = false)
}
