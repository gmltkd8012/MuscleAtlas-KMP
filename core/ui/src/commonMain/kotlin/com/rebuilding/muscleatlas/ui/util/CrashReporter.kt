package com.rebuilding.muscleatlas.ui.util

/**
 * Crash reporting interface for KMP
 * Android: Firebase Crashlytics
 * iOS: Firebase Crashlytics (via Swift)
 */
interface CrashReporter {
    /**
     * Log a message to the crash reporter
     */
    fun log(message: String)

    /**
     * Record a non-fatal exception
     */
    fun recordException(throwable: Throwable)

    /**
     * Set a custom key-value pair for crash reports
     */
    fun setCustomKey(key: String, value: String)

    /**
     * Set the user identifier for crash reports
     */
    fun setUserId(userId: String)
}
