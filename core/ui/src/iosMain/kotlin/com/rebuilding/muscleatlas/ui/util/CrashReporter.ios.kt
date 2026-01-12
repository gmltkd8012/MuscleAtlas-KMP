package com.rebuilding.muscleatlas.ui.util

/**
 * iOS CrashReporter implementation
 * TODO: Firebase Crashlytics iOS SDK 설정 후 실제 구현으로 변경
 */
class IosCrashReporter : CrashReporter {
    override fun log(message: String) {
        // TODO: FirebaseCrashlytics.crashlytics().log(message)
        println("CrashReporter: $message")
    }

    override fun recordException(throwable: Throwable) {
        // TODO: FirebaseCrashlytics.crashlytics().record(error:)
        println("CrashReporter Exception: ${throwable.message}")
        throwable.printStackTrace()
    }

    override fun setCustomKey(key: String, value: String) {
        // TODO: FirebaseCrashlytics.crashlytics().setCustomValue(value, forKey: key)
    }

    override fun setUserId(userId: String) {
        // TODO: FirebaseCrashlytics.crashlytics().setUserID(userId)
    }
}
