package hu.fitpuli.abele.trees

import hu.fitpuli.abele.NativeLogTree
import kotlin.native.concurrent.AtomicInt

class CountingLogTree(minPriority: Int) : NativeLogTree(minPriority) {
    val writeCount = AtomicInt(0)

    override fun writeLog(s: String) {
        writeCount.increment()
    }
}
