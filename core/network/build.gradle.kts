plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.kmp.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.rebuilding.muscleatlas.network"
}

kotlin {
    // Source sets dependencies
    sourceSets.apply {
        commonMain.dependencies {
            // Common dependencies can be added here
        }
        androidMain.dependencies {
            // Android-specific dependencies
        }
        iosMain.dependencies {
            // iOS-specific dependencies
        }
    }
}