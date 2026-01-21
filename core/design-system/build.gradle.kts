plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.kmp.compose)
    alias(libs.plugins.convention.kmp.koin)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.rebuilding.muscleatlas.design_system"
}

compose.resources {
    publicResClass = true
    generateResClass = always
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Coil for image loading (KMP)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
        }
        androidMain.dependencies {
        }
        iosMain.dependencies {
        }
    }
}