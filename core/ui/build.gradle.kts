plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.kmp.compose)
    alias(libs.plugins.convention.kmp.koin)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.rebuilding.muscleatlas.ui"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
        }
        androidMain.dependencies {
            implementation(libs.firebase.crashlytics)
        }
        iosMain.dependencies {
        }
    }
}