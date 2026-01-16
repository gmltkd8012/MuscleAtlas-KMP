plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.kmp.compose)
    alias(libs.plugins.convention.kmp.koin)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.rebuilding.muscleatlas.supabase"
}

kotlin {
    // Source sets dependencies
    sourceSets.apply {
        commonMain.dependencies {
            // Common dependencies can be added here
            api(libs.bundles.supabase)
            implementation(project(":core:app-config"))
        }
        androidMain.dependencies {
            // Android Ktor engine
            implementation(libs.ktor.client.okhttp)
            // In-App Browser
            implementation(libs.kinappbrowser)
        }
        iosMain.dependencies {
            // iOS Ktor engine
            implementation(libs.ktor.client.darwin)
            // In-App Browser
            implementation(libs.kinappbrowser)
        }
    }
}