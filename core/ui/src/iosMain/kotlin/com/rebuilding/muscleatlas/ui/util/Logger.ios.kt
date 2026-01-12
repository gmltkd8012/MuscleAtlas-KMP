package com.rebuilding.muscleatlas.ui.util

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// TODO - 나중에 NSLog 인가 그걸로 변경 예정
actual object Logger : KoinComponent {
    private val crashReporter: CrashReporter by inject()

    actual fun d(tag: String, message: String) {
        println("D/$tag: $message")
    }

    actual fun i(tag: String, message: String) {
        println("I/$tag: $message")
    }

    actual fun w(tag: String, message: String) {
        println("W/$tag: $message")
    }

    actual fun e(tag: String, message: String, throwable: Throwable?) {
        println("E/$tag: $message")
        if (throwable != null) {
            throwable.printStackTrace()
            // Crashlytics에 non-fatal 에러 기록
            crashReporter.log("$tag: $message")
            crashReporter.recordException(throwable)
        } else {
            crashReporter.log("$tag: $message")
        }
    }
}
