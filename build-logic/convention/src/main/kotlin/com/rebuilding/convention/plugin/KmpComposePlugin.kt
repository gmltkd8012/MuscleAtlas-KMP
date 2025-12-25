package com.rebuilding.convention.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.gradle.kotlin.dsl.configure

/**
 * Kotlin Multiplatform + Compose 플러그인
 * 모든 KMP 및 Compose 관련 설정을 담당
 *
 * - androidApp 모듈: android.app + kmp.compose 조합으로 사용
 * - library 모듈: android.library + kmp.compose 조합으로 사용
 */
class KmpComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                // Android target
                androidTarget {
                    compilations.all {
                        kotlinOptions {
                            jvmTarget = "17"
                        }
                    }
                }

                // iOS targets
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

                // Web targets
                js(IR) {
                    browser()
                }

                wasmJs {
                    browser()
                }
            }
        }
    }
}
