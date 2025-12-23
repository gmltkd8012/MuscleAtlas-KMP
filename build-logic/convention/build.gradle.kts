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
        // KMP Library Plugin (for core modules)
        register("kmp-library") {
            id = "kmp.library"
            implementationClass = "com.rebuilding.convention.plugin.KmpLibraryPlugin"
        }

        // KMP Compose Plugin (for shared UI modules)
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
