package com.rebuilding.convention.plugin

import com.android.build.api.dsl.ApplicationExtension
import com.rebuilding.convention.findVersionInt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidAppPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.configure<ApplicationExtension> {
                compileSdk = findVersionInt("compileSdk")

                defaultConfig {
                    minSdk = findVersionInt("minSdk")
                    targetSdk = findVersionInt("compileSdk")
                }

                compileOptions {
                    sourceCompatibility = org.gradle.api.JavaVersion.VERSION_17
                    targetCompatibility = org.gradle.api.JavaVersion.VERSION_17
                }

                kotlinOptions {
                    jvmTarget = "17"
                }

                buildFeatures {
                    compose = true
                }
            }
        }
    }

    private fun ApplicationExtension.kotlinOptions(block: org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions.() -> Unit) {
        (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("kotlinOptions", block)
    }
}
