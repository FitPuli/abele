package hu.fitpuli.abele

import hu.fitpuli.abele.trees.ListTree
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TreeTest {

    @Test
    fun checkIfEnabledTreeCallsPerformLog() {
        var isCalled = false

        val tree = object : Tree() {
            override fun isLoggable(priority: Int, tag: String?): Boolean = true

            override fun performLog(
                priority: Int,
                tag: String?,
                throwable: Throwable?,
                message: String?
            ) {
                isCalled = true
            }
        }

        tree.log(Abele.ASSERT, null, null, null)

        assertTrue(isCalled)
    }

    @Test
    fun checkIfDisabledTreeSkipsPerformLog() {
        var isCalled = false

        val tree = object : Tree() {
            override fun isLoggable(priority: Int, tag: String?): Boolean = false

            override fun performLog(
                priority: Int,
                tag: String?,
                throwable: Throwable?,
                message: String?
            ) {
                isCalled = true
            }
        }

        tree.log(Abele.ASSERT, null, null, null)

        assertFalse(isCalled)
    }

    @Test
    fun checkIfEnabledTreeEvaluatesMessageAndCallsPerformLog() {
        var isCalled = false
        var isEvaluated = false

        val tree = object : Tree() {
            override fun isLoggable(priority: Int, tag: String?): Boolean = true

            override fun performLog(
                priority: Int,
                tag: String?,
                throwable: Throwable?,
                message: String?
            ) {
                isCalled = true
            }
        }

        tree.log(Abele.ASSERT, null) {
            isEvaluated = true
            ""
        }

        assertTrue(isCalled)
        assertTrue(isEvaluated)
    }

    @Test
    fun checkIfDisabledTreeSkipsMessageEvaluationAndPerformLog() {
        var isCalled = false
        var isEvaluated = false

        val tree = object : Tree() {
            override fun isLoggable(priority: Int, tag: String?): Boolean = false

            override fun performLog(
                priority: Int,
                tag: String?,
                throwable: Throwable?,
                message: String?
            ) {
                isCalled = true
            }
        }

        tree.log(Abele.ASSERT, null) {
            isEvaluated = true
            ""
        }

        assertFalse(isCalled)
        assertFalse(isEvaluated)
    }

    @Test
    fun checkLazyAssertMessageEvaluation() {
        val tree = ListTree(allowedLevel = Abele.ASSERT)

        tree.assert { DUMMY_MESSAGE }

        val captured = tree.messages

        assertTrue { captured.isNotEmpty() }
    }

    @Test
    fun checkLazyErrorMessageEvaluation() {
        val tree = ListTree(allowedLevel = Abele.ERROR)

        tree.error { DUMMY_MESSAGE }

        assertTrue { tree.messages.isNotEmpty() }
    }

    @Test
    fun checkLazyWarningMessageEvaluation() {
        val tree = ListTree(allowedLevel = Abele.WARNING)

        tree.warn { DUMMY_MESSAGE }

        assertTrue { tree.messages.isNotEmpty() }
    }

    @Test
    fun checkLazyInfoMessageEvaluation() {
        val tree = ListTree(allowedLevel = Abele.INFO)

        tree.info { DUMMY_MESSAGE }

        assertTrue { tree.messages.isNotEmpty() }
    }

    @Test
    fun checkLazyDebugMessageEvaluation() {
        val tree = ListTree(allowedLevel = Abele.DEBUG)

        tree.debug { DUMMY_MESSAGE }

        assertTrue { tree.messages.isNotEmpty() }
    }

    @Test
    fun checkLazyVerboseMessageEvaluation() {
        val tree = ListTree(allowedLevel = Abele.VERBOSE)

        tree.verbose { DUMMY_MESSAGE }

        assertTrue { tree.messages.isNotEmpty() }
    }

    companion object {
        private const val DUMMY_MESSAGE = "Dummy message"
    }
}
