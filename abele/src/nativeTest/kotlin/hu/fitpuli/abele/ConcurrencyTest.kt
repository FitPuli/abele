package hu.fitpuli.abele

import hu.fitpuli.abele.trees.CountLogTree
import kotlin.native.concurrent.TransferMode
import kotlin.native.concurrent.Worker
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ConcurrencyTest {
    @BeforeTest
    fun setup() {
        Abele.uprootAll()
    }

    @AfterTest
    fun tearDown() {
        Abele.uprootAll()
    }

    @Test
    fun checkLogCountIfLogsAreComingFromMultipleThreads() {
        val countLogTree = CountLogTree(Abele.INFO)
        Abele.plant(countLogTree)

        val workers = Array(COUNT) { Worker.start() }

        Array(workers.size) { workerIndex ->
            workers[workerIndex].execute(TransferMode.SAFE, { LOG_RUNS }) {
                for (i in 0 until it) {
                    Abele.info { "Loggin run $i" }
                }
                return@execute it
            }
        }.forEach { it.result }

        workers.forEach {
            it.requestTermination().consume { _ -> }
        }

        assertEquals(COUNT * LOG_RUNS, countLogTree.writeCount.value)
    }

    companion object {
        const val COUNT = 10
        const val LOG_RUNS = 100000
    }
}

