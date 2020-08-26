package hu.fitpuli.abele

/**
 *  Fehér nyár (Populus alba) erdő :)
 */
object Abele {
    private val forest = Forest(emptyArray())

    val trees: List<Tree>
        get() = forest.trees.toList()

    val size: Int
        get() = forest.trees.size

    fun uprootAll() = forest.update { emptyArray() }

    fun uproot(tree: Tree) = forest.update { planted ->
        val newList = mutableListOf<Tree>()
        var treeFound = false

        for (t in planted) {
            if (t == tree) {
                treeFound = true
            } else {
                newList.add(t)
            }
        }

        require(treeFound) { "Cannot uproot tree which is not planted: $tree" }
        newList.toTypedArray()
    }

    fun plant(tree: Tree) = forest.update { planted ->
        val newList = mutableListOf<Tree>()
        newList.addAll(planted)
        newList.add(tree)
        newList.toTypedArray()
    }

    fun plant(vararg trees: Tree) = forest.update { planted ->
        val newList = mutableListOf<Tree>()
        newList.addAll(planted)
        newList.addAll(trees)
        newList.toTypedArray()
    }

    fun plantAll(trees: Iterable<Tree>) = forest.update { planted ->
        val newList = mutableListOf<Tree>()
        newList.addAll(planted)
        newList.addAll(trees)
        newList.toTypedArray()
    }

    fun isLoggable(priority: Int, tag: String? = null): Boolean =
        forest.trees.any { it.isLoggable(priority, tag) }

    fun log(priority: Int, tag: String?, throwable: Throwable?, message: String?) {
        forest.trees.forEach { it.log(priority, tag, throwable, message) }
    }

    @PublishedApi
    internal fun rawLog(priority: Int, tag: String?, throwable: Throwable?, message: String?) {
        forest.trees.forEach { it.rawLog(priority, tag, throwable, message) }
    }

    fun tagged(tag: String): Tree {
        val taggedTag = tag
        return object : Tree() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return Abele.isLoggable(priority, tag ?: taggedTag)
            }

            override fun performLog(priority: Int, tag: String?, throwable: Throwable?, message: String?) {
                Abele.log(priority, tag ?: taggedTag, throwable, message)
            }
        }
    }

    inline fun <reified T> tagged(): Tree = tagged(T::class.simpleName ?: "")

    const val VERBOSE = 2
    const val DEBUG = 3
    const val INFO = 4
    const val WARNING = 5
    const val ERROR = 6
    const val ASSERT = 7
}

inline fun Abele.log(priority: Int, throwable: Throwable? = null, message: () -> String) {
    if (isLoggable(priority, null)) {
        rawLog(priority, null, throwable, message())
    }
}

inline fun Abele.assert(throwable: Throwable? = null, message: () -> String) {
    log(ASSERT, throwable, message)
}

inline fun Abele.error(throwable: Throwable? = null, message: () -> String) {
    log(ERROR, throwable, message)
}

inline fun Abele.warn(throwable: Throwable? = null, message: () -> String) {
    log(WARNING, throwable, message)
}

inline fun Abele.info(throwable: Throwable? = null, message: () -> String) {
    log(INFO, throwable, message)
}

inline fun Abele.debug(throwable: Throwable? = null, message: () -> String) {
    log(DEBUG, throwable, message)
}

inline fun Abele.verbose(throwable: Throwable? = null, message: () -> String) {
    log(VERBOSE, throwable, message)
}
