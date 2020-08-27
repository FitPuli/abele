package hu.fitpuli.abele

import hu.fitpuli.abele.Abele.ASSERT
import hu.fitpuli.abele.Abele.DEBUG
import hu.fitpuli.abele.Abele.ERROR
import hu.fitpuli.abele.Abele.INFO
import hu.fitpuli.abele.Abele.VERBOSE
import hu.fitpuli.abele.Abele.WARNING

/**
 * Base class for processing logs.
 */
abstract class Tree {
    /**
     * Returns true when [priority] will be logged. Behavior is undefined for values other than
     * [Abele.ASSERT], [Abele.ERROR], [Abele.WARNING], [Abele.INFO], [Abele.DEBUG] or [Abele.VERBOSE].
     */
    open fun isLoggable(priority: Int, tag: String? = null): Boolean = true

    /**
     * Creates a log with the given [priority] and optional [tag], [throwable], [message].
     *
     * @param priority - log priority ([ASSERT], [ERROR], [WARNING], [INFO], [DEBUG] or [VERBOSE])
     * @param tag - an optional tag
     * @param throwable - an optional [Throwable]
     * @param message - optional log message
     */
    fun log(priority: Int, tag: String?, throwable: Throwable?, message: String?) {
        if (isLoggable(priority, tag)) {
            performLog(priority, tag, throwable, message)
        }
    }

    /**
     * Invoke only when [isLoggable] has returned true. This is only for internal use!
     */
    @PublishedApi
    internal fun rawLog(priority: Int, tag: String?, throwable: Throwable?, message: String?) {
        performLog(priority, tag, throwable, message)
    }

    /**
     * Processes a log with the given [priority] and optional [tag], [throwable], [message].
     *
     * @param priority - log priority ([ASSERT], [ERROR], [WARNING], [INFO], [DEBUG] or [VERBOSE])
     * @param tag - an optional tag
     * @param throwable - an optional [Throwable]
     * @param message - optional log message
     */
    protected abstract fun performLog(
        priority: Int,
        tag: String?,
        throwable: Throwable?,
        message: String?
    )
}

/**
 * Creates a lazy evaluated log with the given [priority], optional [throwable] and a lazy
 * evaluated [message] block.
 *
 * @param priority - log priority ([ASSERT], [ERROR], [WARNING], [INFO], [DEBUG] or [VERBOSE])
 * @param throwable - an optional [Throwable]
 * @param message - the log message factory block
 */
inline fun Tree.log(priority: Int, throwable: Throwable? = null, message: () -> String) {
    if (isLoggable(priority, null)) {
        rawLog(priority, null, throwable, message())
    }
}

/**
 * Creates a lazy evaluated assert log.
 *
 * @param throwable - an optional [Throwable]
 * @param message - the log message factory block
 */
inline fun Tree.assert(throwable: Throwable? = null, message: () -> String) {
    log(ASSERT, throwable, message)
}

/**
 * Creates a lazy evaluated error log.
 *
 * @param throwable - an optional [Throwable]
 * @param message - the log message factory block
 */
inline fun Tree.error(throwable: Throwable? = null, message: () -> String) {
    log(ERROR, throwable, message)
}

/**
 * Creates a lazy evaluated warning log.
 *
 * @param throwable - an optional [Throwable]
 * @param message - the log message factory block
 */
inline fun Tree.warn(throwable: Throwable? = null, message: () -> String) {
    log(WARNING, throwable, message)
}

/**
 * Creates a lazy evaluated info log.
 *
 * @param throwable - an optional [Throwable]
 * @param message - the log message factory block
 */
inline fun Tree.info(throwable: Throwable? = null, message: () -> String) {
    log(INFO, throwable, message)
}

/**
 * Creates a lazy evaluated debug log.
 *
 * @param throwable - an optional [Throwable]
 * @param message - the log message factory block
 */
inline fun Tree.debug(throwable: Throwable? = null, message: () -> String) {
    log(DEBUG, throwable, message)
}

/**
 * Creates a lazy evaluated verbose log.
 *
 * @param throwable - an optional [Throwable]
 * @param message - the log message factory block
 */
inline fun Tree.verbose(throwable: Throwable? = null, message: () -> String) {
    log(VERBOSE, throwable, message)
}
