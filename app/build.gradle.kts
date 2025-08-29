import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    alias(libs.plugins.application)
    alias(libs.plugins.app.flavor)
    alias(libs.plugins.all.hilt)
    alias(libs.plugins.app.compose)
}

android {
    namespace = "com.rebuilding.muscleatlas"
    compileSdk = 36

    val appKeyAlias = System.getenv("APP_KEY_ALIAS") ?: gradleLocalProperties(rootDir, providers).getProperty("muscle_atlas_key_alias")
    val appKeyPassword = System.getenv("APP_KEY_PASSWORD") ?: gradleLocalProperties(rootDir, providers).getProperty("muscle_atlas_key_password")

    defaultConfig {
        applicationId = "com.rebuilding.muscleatlas"
        minSdk = 24
        targetSdk = 36
        versionCode = libs.versions.appVersionCode.get().toInt()
        versionName = libs.versions.appVersionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "APP_KEY_ALIAS", appKeyAlias)
        buildConfigField("String", "APP_KEY_PASSWORD", appKeyPassword)
    }

    signingConfigs {
        create("release") {
            storeFile = file("../keystore/platform.jks")
            storePassword = appKeyPassword
            keyAlias = appKeyAlias
            keyPassword = appKeyPassword
        }
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    applicationVariants.configureEach {
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val today = dateFormat.format(Date())
        val appName = "muscle_atlas"
        val versionName = defaultConfig.versionName.orEmpty()

        this.outputs.configureEach {
            buildOutputs.forEach { _ ->
                val archivesBaseName = "$appName-$flavorName-${buildType.name}-v$versionName-$today.apk"
                val variantOutputImpl = this as BaseVariantOutputImpl
                variantOutputImpl.outputFileName = archivesBaseName
            }
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

    implementation(libs.android.material)
}