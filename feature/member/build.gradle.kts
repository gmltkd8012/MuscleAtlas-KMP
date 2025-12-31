plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.kmp.compose)
    alias(libs.plugins.convention.kmp.koin)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.rebuilding.muscleatlas.member"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:ui"))
            implementation(project(":core:data"))
        }
        androidMain.dependencies {
        }
        iosMain.dependencies {
        }
    }
}
