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
}

gradlePlugin {
    plugins {
        register("android-hilt") {
            id = "all.hilt"
            implementationClass = "com.rebuilding.convention.plugin.AndroidHiltPlugin"
        }
    }

    plugins {
        register("application") {
            id = "app.application"
            implementationClass = "com.rebuilding.convention.plugin.ApplicationConventionPlugin"
        }
    }

    plugins {
        register("application-flavor") {
            id = "app.flavor"
            implementationClass = "com.rebuilding.convention.plugin.AppFlavorConventionPlugin"
        }
    }

    plugins {
        register("application-compose") {
            id = "app.compose"
            implementationClass = "com.rebuilding.convention.plugin.ApplicationComposePlugin"
        }
    }

    plugins {
        register("library") {
            id = "lib.library"
            implementationClass = "com.rebuilding.convention.plugin.LibraryConventionPlugin"
        }
    }

    plugins {
        register("library-flavor") {
            id = "lib.flavor"
            implementationClass = "com.rebuilding.convention.plugin.LibraryFlavorConventionPlugin"
        }
    }

    plugins {
        register("library-compose") {
            id = "lib.compose"
            implementationClass = "com.rebuilding.convention.plugin.LibraryComposePlugin"
        }
    }

    plugins {
        register("library-room") {
            id = "lib.room"
            implementationClass = "com.rebuilding.convention.plugin.AndroidRoomPlugin"
        }
    }
}