plugins {
    alias(libs.plugins.application)
    alias(libs.plugins.app.flavor)
    alias(libs.plugins.all.hilt)
    alias(libs.plugins.app.compose)
}

android {
    namespace = "com.rebuilding.muscleatlas"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.rebuilding.muscleatlas"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:design-system"))
    implementation(project(":core:model"))
    implementation(project(":core:datastore"))
    implementation(project(":feature:main"))
}