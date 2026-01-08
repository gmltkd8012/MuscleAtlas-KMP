plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.kmp.compose)
    alias(libs.plugins.convention.kmp.koin)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.rebuilding.muscleatlas.util"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
        }
        androidMain.dependencies {
            // Kakao SDK for sharing
            implementation(libs.bundles.kakaoShare)
            implementation(project(":core:ui"))
        }
        iosMain.dependencies {
        }
    }
}
