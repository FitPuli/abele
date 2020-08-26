package hu.fitpuli.abele

abstract class NativeLogTree(private val minPriority: Int) : Tree() {
    override fun performLog(priority: Int, tag: String?, throwable: Throwable?, message: String?) {
        val bigMessage = StringBuilder()
        if (tag != null) {
            bigMessage.append("[$tag] ")
        }
        if (message != null) {
            bigMessage.append(message)
        }
        if (throwable != null) {
            val stacktrace: Array<String> = throwable.getStackTrace()

            bigMessage.append("\n$throwable")
            for (element in stacktrace) {
                bigMessage.append("\n        at $element")
            }
        }

        writeLog(bigMessage.toString())
    }

    abstract fun writeLog(s: String)

    override fun isLoggable(priority: Int, tag: String?): Boolean = minPriority <= priority
}
