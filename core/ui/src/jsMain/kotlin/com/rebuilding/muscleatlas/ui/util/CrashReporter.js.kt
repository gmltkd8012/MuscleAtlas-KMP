package com.rebuilding.muscleatlas.ui.util

/**
 * JS CrashReporter implementation (no-op)
 */
class JsCrashReporter : CrashReporter {
    override fun log(message: String) {
        println("CrashReporter: $message")
    }

    override fun recordException(throwable: Throwable) {
        println("CrashReporter Exception: ${throwable.message}")
    }

    override fun setCustomKey(key: String, value: String) {
        // no-op
    }

    override fun setUserId(userId: String) {
        // no-op
    }
}
