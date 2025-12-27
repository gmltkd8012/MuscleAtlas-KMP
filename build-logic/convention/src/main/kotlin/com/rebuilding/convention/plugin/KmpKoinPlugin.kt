package com.rebuilding.convention.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Koin DI 플러그인 (KMP 호환)
 *
 * Koin 관련 의존성을 각 source set에 맞게 설정
 * - commonMain: koin-core, koin-compose, koin-compose-viewmodel
 * - androidMain: koin-android
 */
class KmpKoinPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.apply {
                    commonMain.dependencies {
                        implementation(libs.findLibrary("koin-core").get())
                        implementation(libs.findLibrary("koin-compose").get())
                        implementation(libs.findLibrary("koin-compose-viewmodel").get())
                    }
                    androidMain.dependencies {
                        implementation(libs.findLibrary("koin-android").get())
                    }
                }
            }
        }
    }
}
