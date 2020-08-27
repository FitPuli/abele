package hu.fitpuli.abele

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NativeLogTreeTest {
    @BeforeTest
    fun setup() {
        Abele.uprootAll()
        LogSaver.logs.clear()
    }

    @AfterTest
    fun tearDown() {
        Abele.uprootAll()
    }

    @Test
    fun basicTest() {
        Abele.plant(SaveLogTree(Abele.INFO))
        Abele.warn { "Hello" }

        assertEquals(LogSaver.logs, listOf("Hello"))
    }

    @Test
    fun priorityTest() {
        Abele.plant(SaveLogTree(Abele.WARNING))
        Abele.info { "Hello" }

        assertEquals(LogSaver.logs, emptyArray<String>().toMutableList())
    }

    @Test
    fun exceptionTest() {
        Abele.plant(SaveLogTree(Abele.WARNING))
        Abele.warn(NullPointerException()) { "Hello" }
        assertTrue(LogSaver.logs.any { it.contains("kfun:hu.fitpuli.abele.NativeLogTreeTest#exceptionTest()") })
    }
}

class SaveLogTree(minPriority: Int) : NativeLogTree(minPriority) {
    override fun writeLog(s: String) {
        LogSaver.write(s)
    }
}

@ThreadLocal
object LogSaver {

    fun write(s: String) {
        logs.add(s)
    }

    val logs = mutableListOf<String>()
}
