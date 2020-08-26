package hu.fitpuli.abele

import hu.fitpuli.abele.trees.DummyTree
import hu.fitpuli.abele.trees.ListTree
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AbeleTest {
    @BeforeTest
    fun setup() {
        Abele.uprootAll()
    }

    @AfterTest
    fun tearDown() {
        Abele.uprootAll()
    }

    @Test
    fun checkForestSize() {
        assertEquals(0, Abele.size)

        for (i in 1..50) {
            Abele.plant(ListTree())
            assertEquals(i, Abele.size)
        }

        assertEquals(50, Abele.size)

        Abele.uprootAll()
        assertEquals(0, Abele.size)
    }

    @Test
    fun checkSequentialPlanting() {
        val one = ListTree()
        Abele.plant(one)
        val two = ListTree()
        Abele.plant(two)

        assertEquals(listOf(one, two), Abele.trees)
    }

    @Test
    fun checkVarargPlanting() {
        val one = ListTree()
        val two = ListTree()
        Abele.plant(one, two)

        assertEquals(listOf(one, two), Abele.trees)
    }

    @Test
    fun checkPlantAll() {
        val one = ListTree()
        val two = ListTree()
        Abele.plantAll(listOf(one, two))

        assertEquals(listOf(one, two), Abele.trees)
    }

    @Test
    fun checkIfUprootThrowsIfForestIsEmpty() {
        val tree = ListTree()
        assertFailsWith(IllegalArgumentException::class,
            "Cannot uproot tree which is not planted: $tree") {
            Abele.uproot(tree)
        }
    }

    @Test
    fun checkIfUprootRemovesTreeIfTreeWasAdded() {
        val tree = ListTree()

        Abele.plant(tree)
        assertEquals(listOf(tree), Abele.trees)

        Abele.uproot(tree)
        assertTrue(Abele.trees.isEmpty())
    }

    @Test
    fun checkIfUprootRemovesCorrectTreeIfMultipleTreeWasAdded() {
        val one = ListTree()
        val two = ListTree()
        Abele.plantAll(listOf(one, two))

        Abele.uproot(one)
        assertEquals(listOf(two), Abele.trees)
    }

    @Test
    fun checkIfAbeleCantLogIfTheresNoLoggableTreeProvided() {
        val one = DummyTree.Disabled()
        val two = DummyTree.Disabled()
        Abele.plantAll(listOf(one, two))

        assertFalse { Abele.isLoggable(Abele.ASSERT) }
        assertFalse { Abele.isLoggable(Abele.ERROR) }
        assertFalse { Abele.isLoggable(Abele.WARNING) }
        assertFalse { Abele.isLoggable(Abele.DEBUG) }
        assertFalse { Abele.isLoggable(Abele.INFO) }
        assertFalse { Abele.isLoggable(Abele.VERBOSE) }
    }

    @Test
    fun checkIfAbeleCanLogIfTheresAtleastOneLoggableTreeProvided() {
        val one = DummyTree.Disabled()
        val two = DummyTree.Enabled()
        Abele.plantAll(listOf(one, two))

        assertTrue { Abele.isLoggable(Abele.ASSERT) }
        assertTrue { Abele.isLoggable(Abele.ERROR) }
        assertTrue { Abele.isLoggable(Abele.WARNING) }
        assertTrue { Abele.isLoggable(Abele.DEBUG) }
        assertTrue { Abele.isLoggable(Abele.INFO) }
        assertTrue { Abele.isLoggable(Abele.VERBOSE) }
    }

    @Test
    fun checkIfAbeleCanLogIfMultipleTreesAreLoggable() {
        val one = DummyTree.Enabled()
        val two = DummyTree.Enabled()
        Abele.plantAll(listOf(one, two))

        assertTrue { Abele.isLoggable(Abele.ASSERT) }
        assertTrue { Abele.isLoggable(Abele.ERROR) }
        assertTrue { Abele.isLoggable(Abele.WARNING) }
        assertTrue { Abele.isLoggable(Abele.DEBUG) }
        assertTrue { Abele.isLoggable(Abele.INFO) }
        assertTrue { Abele.isLoggable(Abele.VERBOSE) }
    }
}
