
plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.kmp.compose)
    alias(libs.plugins.convention.kmp.koin)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.rebuilding.muscleatlas.app"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Core modules
            implementation(project(":core:supabase"))
            implementation(project(":core:ui"))
            implementation(project(":core:design-system"))
            implementation(project(":feature:splash"))
            implementation(project(":feature:login"))
            implementation(project(":feature:member"))
            implementation(project(":feature:setting"))
            implementation(project(":feature:workout"))
        }
        androidMain.dependencies {
        }
        iosMain.dependencies {
        }
    }
}