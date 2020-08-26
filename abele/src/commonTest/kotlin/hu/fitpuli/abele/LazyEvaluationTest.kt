package hu.fitpuli.abele

import hu.fitpuli.abele.trees.DummyTree
import kotlin.test.*

class LazyEvaluationTest {
    @BeforeTest
    fun setup() {
        Abele.uprootAll()
    }

    @AfterTest
    fun tearDown() {
        Abele.uprootAll()
    }

    @Test
    fun checkIfMessageEvaluationIsSkippedIfForestIsEmptyThusLoggingIsDisabled() {
        var isEvaluated = false

        Abele.assert {
            isEvaluated = true
            ""
        }

        assertFalse(isEvaluated)
    }

    @Test
    fun checkIfMessageEvaluationIsSkippedIfLoggingIsDisabled() {
        var isEvaluated = false

        Abele.plant(DummyTree.Disabled())

        Abele.assert {
            isEvaluated = true
            ""
        }

        assertFalse(isEvaluated)
    }

    @Test
    fun checkIfMessageEvaluationIsPerformedIfLoggingIsEnabled() {
        var isEvaluated = false

        Abele.plant(DummyTree.Enabled())

        Abele.assert {
            isEvaluated = true
            ""
        }

        assertTrue(isEvaluated)
    }
}