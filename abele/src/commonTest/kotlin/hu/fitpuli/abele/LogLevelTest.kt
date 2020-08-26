package hu.fitpuli.abele

import hu.fitpuli.abele.trees.LogLevelTree
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LogLevelTest {

    @BeforeTest
    fun setup() {
        Abele.uprootAll()
    }

    @AfterTest
    fun tearDown() {
        Abele.uprootAll()
    }

    @Test
    fun checkAssertPropagation() {
        checkPropagation(Abele.ASSERT) {
            Abele.assert { LOG_MESSAGE }
        }
    }

    @Test
    fun checkAssertTaggedPropagation() {
        checkPropagation(Abele.ASSERT) {
            Abele.tagged(TAG).assert { LOG_MESSAGE }
        }
    }

    @Test
    fun checkDebugPropagation() {
        checkPropagation(Abele.DEBUG) {
            Abele.debug { LOG_MESSAGE }
        }
    }

    @Test
    fun checkDebugTaggedPropagation() {
        checkPropagation(Abele.DEBUG) {
            Abele.tagged(TAG).debug { LOG_MESSAGE }
        }
    }

    @Test
    fun checkWarnPropagation() {
        checkPropagation(Abele.WARNING) {
            Abele.warn { LOG_MESSAGE }
        }
    }

    @Test
    fun checkWarnTaggedPropagation() {
        checkPropagation(Abele.WARNING) {
            Abele.tagged(TAG).warn { LOG_MESSAGE }
        }
    }

    @Test
    fun checkInfoPropagation() {
        checkPropagation(Abele.INFO) {
            Abele.info { LOG_MESSAGE }
        }
    }

    @Test
    fun checkInfoTaggedPropagation() {
        checkPropagation(Abele.INFO) {
            Abele.tagged(TAG).info { LOG_MESSAGE }
        }
    }

    @Test
    fun checkErrorPropagation() {
        checkPropagation(Abele.ERROR) {
            Abele.error { LOG_MESSAGE }
        }
    }

    @Test
    fun checkErrorTaggedPropagation() {
        checkPropagation(Abele.ERROR) {
            Abele.tagged(TAG).error { LOG_MESSAGE }
        }
    }

    @Test
    fun checkVerbosePropagation() {
        checkPropagation(Abele.VERBOSE) {
            Abele.verbose { LOG_MESSAGE }
        }
    }

    @Test
    fun checkVerboseTaggedPropagation() {
        checkPropagation(Abele.VERBOSE) {
            Abele.tagged(TAG).verbose { LOG_MESSAGE }
        }
    }

    private fun checkPropagation(logLevel: Int, block: () -> Unit) {
        val tree = LogLevelTree()
        Abele.plant(tree)

        block()

        assertEquals(1, tree.logs.size)
        assertEquals(logLevel, tree.logs.first())
    }

    companion object {
        const val TAG = "TestTag"
        const val LOG_MESSAGE = "TestLogMessage"
    }
}