package com.rebuilding.convention.plugin

import com.rebuilding.convention.findVersion
import com.rebuilding.convention.findVersionInt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.gradle.kotlin.dsl.configure

class KmpLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.library")
            }

            extensions.configure<KotlinMultiplatformExtension> {
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
                        baseName = project.name
                        isStatic = true
                    }
                }

                js(IR) {
                    browser()
                }

                wasmJs {
                    browser()
                }

                sourceSets.apply {
                    commonMain.dependencies {
                        // Common dependencies can be added here
                    }
                    androidMain.dependencies {
                        // Android-specific dependencies
                    }
                    iosMain.dependencies {
                        // iOS-specific dependencies
                    }
                }
            }

            extensions.configure<com.android.build.gradle.LibraryExtension> {
                compileSdk = findVersionInt("compileSdk")

                defaultConfig {
                    minSdk = findVersionInt("minSdk")
                }

                compileOptions {
                    sourceCompatibility = org.gradle.api.JavaVersion.VERSION_17
                    targetCompatibility = org.gradle.api.JavaVersion.VERSION_17
                }
            }
        }
    }
}
