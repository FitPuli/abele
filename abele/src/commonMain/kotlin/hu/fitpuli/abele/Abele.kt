package hu.fitpuli.abele

/**
 * This is the main entry point of the logger. This global instance holds reference to the populated [forest]
 * of [Tree] instances.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Populus_alba">Named after Populus alba tree.</a>
 */
object Abele {
    private val forest = Forest(emptyArray())

    /**
     * List the currently planted [Tree] instances in [Abele.forest].
     *
     * @see Abele.plant
     * @see Tree
     */
    val trees: List<Tree>
        get() = forest.trees.toList()

    /**
     * Get the currently planted [Tree] count in [Abele.forest].
     *
     * @see Abele.plant
     * @see Tree
     */
    val size: Int
        get() = forest.trees.size

    /**
     * Removes all [Tree] instances from [Abele.forest].
     */
    fun uprootAll() = forest.update { emptyArray() }

    /**
     * Removes the provided [tree] instance from [Abele.forest]. If the [Tree] instance was not
     * part of the [forest] then this method throws an [IllegalArgumentException]!
     *
     * @throws IllegalArgumentException if the [tree] was not found in the [forest]
     */
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

    /**
     * Insert the [tree] instance to the [Abele.forest].
     */
    fun plant(tree: Tree) = forest.update { planted ->
        val newList = mutableListOf<Tree>()
        newList.addAll(planted)
        newList.add(tree)
        newList.toTypedArray()
    }

    /**
     * Insert all [Tree] instances from [trees] to the [Abele.forest].
     */
    fun plant(vararg trees: Tree) = forest.update { planted ->
        val newList = mutableListOf<Tree>()
        newList.addAll(planted)
        newList.addAll(trees)
        newList.toTypedArray()
    }

    /**
     * Insert all [Tree] instances from [trees] to the [Abele.forest].
     */
    fun plantAll(trees: Iterable<Tree>) = forest.update { planted ->
        val newList = mutableListOf<Tree>()
        newList.addAll(planted)
        newList.addAll(trees)
        newList.toTypedArray()
    }

    /**
     * Determines whether the provided [priority] with an optional [tag] is loggable by any [Tree]
     * instance in the [Abele.forest].
     */
    fun isLoggable(priority: Int, tag: String? = null): Boolean =
        forest.trees.any { it.isLoggable(priority, tag) }

    /**
     * Forwards a log request to all [Tree] instances in the [Abele.forest] with the given
     * [priority] and optional [tag], [throwable] and [message].
     *
     * The provided [Tree] instances will determine if the received log should be precessed or not.
     *
     * @see Tree.isLoggable
     */
    fun log(priority: Int, tag: String?, throwable: Throwable?, message: String?) {
        forest.trees.forEach { it.log(priority, tag, throwable, message) }
    }

    /**
     * Creates a new [Tree] instance which will use the given [tag] as a fallback for all logs.
     *
     * [Tree.isLoggable] behaviour matches [Abele.isLoggable]
     */
    fun tagged(tag: String): Tree {
        val taggedTag = tag
        return object : Tree() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return Abele.isLoggable(priority, tag ?: taggedTag)
            }

            override fun performLog(
                priority: Int,
                tag: String?,
                throwable: Throwable?,
                message: String?
            ) {
                Abele.log(priority, tag ?: taggedTag, throwable, message)
            }
        }
    }

    /**
     * Creates a new [Tree] instance which will use the given [T] class's name as a fallback tag for
     * all logs.
     *
     * [Tree.isLoggable] behaviour matches [Abele.isLoggable]
     */
    inline fun <reified T> tagged(): Tree = tagged(T::class.simpleName ?: "")

    /**
     * Priority constant for verbose level logs.
     */
    const val VERBOSE = 2

    /**
     * Priority constant for debug level logs.
     */
    const val DEBUG = 3

    /**
     * Priority constant for info level logs.
     */
    const val INFO = 4

    /**
     * Priority constant for warning level logs.
     */
    const val WARNING = 5

    /**
     * Priority constant for error level logs.
     */
    const val ERROR = 6

    /**
     * Priority constant for assert level logs.
     */
    const val ASSERT = 7
}

/**
 * Forwards a log request to all [Tree] instances in the [Abele.forest] with the given
 * [priority], optional [throwable] and a lazy evaluated [message] block.
 *
 * The provided [Tree] instances will determine if the received log should be precessed or not.
 *
 * @see Tree.isLoggable
 */
inline fun Abele.log(priority: Int, throwable: Throwable? = null, message: () -> String) {
    trees.fold<Tree, String?>(null) { lazyMessage, tree ->
        if (tree.isLoggable(priority, null)) {
            val evaluatedMessage = lazyMessage ?: message()
            tree.rawLog(priority, null, throwable, evaluatedMessage)
            return@fold evaluatedMessage
        } else {
            return@fold lazyMessage
        }
    }
}

/**
 * Creates a lazy evaluated assert log.
 *
 * @param throwable - an optional [Throwable]
 * @param message - the log message factory block
 */
inline fun Abele.assert(throwable: Throwable? = null, message: () -> String) {
    log(ASSERT, throwable, message)
}

/**
 * Creates a lazy evaluated error log.
 *
 * @param throwable - an optional [Throwable]
 * @param message - the log message factory block
 */
inline fun Abele.error(throwable: Throwable? = null, message: () -> String) {
    log(ERROR, throwable, message)
}

/**
 * Creates a lazy evaluated warning log.
 *
 * @param throwable - an optional [Throwable]
 * @param message - the log message factory block
 */
inline fun Abele.warn(throwable: Throwable? = null, message: () -> String) {
    log(WARNING, throwable, message)
}

/**
 * Creates a lazy evaluated info log.
 *
 * @param throwable - an optional [Throwable]
 * @param message - the log message factory block
 */
inline fun Abele.info(throwable: Throwable? = null, message: () -> String) {
    log(INFO, throwable, message)
}

/**
 * Creates a lazy evaluated debug log.
 *
 * @param throwable - an optional [Throwable]
 * @param message - the log message factory block
 */
inline fun Abele.debug(throwable: Throwable? = null, message: () -> String) {
    log(DEBUG, throwable, message)
}

/**
 * Creates a lazy evaluated verbose log.
 *
 * @param throwable - an optional [Throwable]
 * @param message - the log message factory block
 */
inline fun Abele.verbose(throwable: Throwable? = null, message: () -> String) {
    log(VERBOSE, throwable, message)
}
