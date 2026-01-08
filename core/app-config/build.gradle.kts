import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.util.Properties

plugins {
    alias(libs.plugins.convention.android.library)
    alias(libs.plugins.convention.kmp.compose)
    alias(libs.plugins.convention.kmp.koin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.buildkonfig)
}

android {
    namespace = "com.rebuilding.muscleatlas.appconfig"
}

buildkonfig {
    packageName = "com.rebuilding.muscleatlas.appconfig"

    val localProperties = Properties().apply {
        val file = rootProject.file("local.properties")
        if (file.exists()) file.inputStream().use { load(it) }
    }

    defaultConfigs {
        buildConfigField(STRING, "SUPABASE_URL", localProperties.getProperty("SUPABASE_URL") ?: "")
        buildConfigField(
            STRING,
            "SUPABASE_ANON_KEY",
            localProperties.getProperty("SUPABASE_ANON_KEY") ?: ""
        )
        buildConfigField(
            STRING,
            "GOOGLE_SERVER_CLIENT_ID",
            localProperties.getProperty("GOOGLE_SERVER_CLIENT_ID") ?: ""
        )

        // Kakao SDK Native App Key
        buildConfigField(
            STRING,
            "KAKAO_NATIVE_APP_KEY",
            localProperties.getProperty("KAKAO_NATIVE_APP_KEY", "") ?: "")
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
        }
        androidMain.dependencies {
        }
        iosMain.dependencies {
        }
    }
}
