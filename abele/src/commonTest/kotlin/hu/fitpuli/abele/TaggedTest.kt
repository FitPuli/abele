package hu.fitpuli.abele

import hu.fitpuli.abele.trees.DummyTree
import hu.fitpuli.abele.trees.ListTree
import kotlin.test.*

class TaggedTest {
    @BeforeTest
    fun setup() {
        Abele.uprootAll()
    }

    @AfterTest
    fun tearDown() {
        Abele.uprootAll()
    }

    @Test
    fun checkEnabledLoggingPropagation() {
        val tree = DummyTree.Enabled()

        Abele.plant(tree)

        assertTrue { Abele.tagged(TAG).isLoggable(Abele.ASSERT) }
    }

    @Test
    fun checkDisabledLoggingPropagation() {
        val tree = DummyTree.Disabled()

        Abele.plant(tree)

        assertFalse { Abele.tagged(TAG).isLoggable(Abele.ASSERT) }
    }

    @Test
    fun checkMessagePropagation() {
        val tree = ListTree()

        Abele.plant(tree)

        Abele.tagged(TAG).verbose { "" }

        assertTrue { tree.messages.isNotEmpty() }
        assertTrue { tree.messages.first().contains(TAG) }
    }

    companion object {
        private const val TAG = "TestTag"
    }
}