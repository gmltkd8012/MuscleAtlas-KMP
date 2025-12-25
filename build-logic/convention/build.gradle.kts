plugins {
    `kotlin-dsl`
}

kotlinDslPluginOptions {
    jvmTarget.set("17")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

dependencies {
    compileOnly(libs.android.tools.build.gradle)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.compose.multiplatform.gradle.plugin)
}

gradlePlugin {
    plugins {
        // Android Library Plugin (for library modules)
        register("android-library") {
            id = "android.library"
            implementationClass = "com.rebuilding.convention.plugin.AndroidLibraryPlugin"
        }

        // KMP Compose Plugin (for KMP + Compose setup)
        register("kmp-compose") {
            id = "kmp.compose"
            implementationClass = "com.rebuilding.convention.plugin.KmpComposePlugin"
        }

        // Android App Plugin (for androidApp entry point)
        register("android-app") {
            id = "android.app"
            implementationClass = "com.rebuilding.convention.plugin.AndroidAppPlugin"
        }
    }
}
