plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "datastore"
            isStatic = true
        }
    }

    js(IR) { browser() }
    wasmJs { browser() }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.koin.core)
            // TODO: Add multiplatform-settings for KMP DataStore alternative
            // implementation("com.russhwolf:multiplatform-settings:1.2.0")
            // implementation("com.russhwolf:multiplatform-settings-coroutines:1.2.0")
        }
        androidMain.dependencies {
            implementation(libs.androidx.datastore.preferences)
        }
        iosMain.dependencies {
        }
    }
}

android {
    namespace = "com.rebuilding.muscleatlas.datastore"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
