package com.rebuilding.muscleatlas.ui.util

import android.util.Log
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual object Logger : KoinComponent {
    private val crashReporter: CrashReporter by inject()

    actual fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    actual fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    actual fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

    actual fun e(tag: String, message: String, throwable: Throwable?) {
        if (throwable != null) {
            Log.e(tag, message, throwable)
            // Crashlytics에 non-fatal 에러 기록
            crashReporter.log("$tag: $message")
            crashReporter.recordException(throwable)
        } else {
            Log.e(tag, message)
            // throwable 없어도 에러 로그는 기록
            crashReporter.log("$tag: $message")
        }
    }
}
