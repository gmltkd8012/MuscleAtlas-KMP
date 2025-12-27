plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.kmp.compose)
    alias(libs.plugins.convention.kmp.koin)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.rebuilding.muscleatlas.design_system"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Compose dependencies are provided by kmp.compose plugin
        }
        androidMain.dependencies {
        }
        iosMain.dependencies {
        }
    }
}