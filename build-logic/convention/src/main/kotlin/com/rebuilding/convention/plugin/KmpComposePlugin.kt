package com.rebuilding.convention.plugin

import com.rebuilding.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

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

            val compose = extensions.getByType<ComposeExtension>().dependencies

            // libs 카탈로그에서 의존성 미리 가져오기
            val navigationCompose = libs.findLibrary("navigation-compose").get()
            val lifecycleViewModelCompose = libs.findLibrary("lifecycle-viewmodel-compose").get()
            val lifecycleRuntimeCompose = libs.findLibrary("lifecycle-runtime-compose").get()

            extensions.configure<KotlinMultiplatformExtension> {
                // Android target
                androidTarget {
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_17)
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

                // Compose dependencies for all source sets
                sourceSets.apply {
                    commonMain.dependencies {
                        // Compose
                        implementation(compose.runtime)
                        implementation(compose.foundation)
                        implementation(compose.material3)
                        implementation(compose.materialIconsExtended)
                        implementation(compose.ui)
                        implementation(compose.components.resources)
                        implementation(compose.components.uiToolingPreview)

                        // Navigation (KMP)
                        implementation(navigationCompose)

                        // Lifecycle (KMP)
                        implementation(lifecycleViewModelCompose)
                        implementation(lifecycleRuntimeCompose)
                    }
                }
            }
        }
    }
}
