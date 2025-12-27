package com.rebuilding.convention.plugin

import com.android.build.api.dsl.ApplicationExtension
import com.rebuilding.convention.findVersionInt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Android Application 모듈 전용 플러그인
 * KMP를 사용하는 경우 kmp.compose 플러그인과 함께 사용
 */
class AndroidAppPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
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

                buildFeatures {
                    compose = true
                }
            }
        }
    }
}
