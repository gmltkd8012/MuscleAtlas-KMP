package com.rebuilding.convention.plugin

import com.android.build.api.dsl.ApplicationExtension
import com.rebuilding.convention.configureFlavorDefault
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AppFlavorConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<ApplicationExtension> {
                configureFlavorDefault(this)
            }
        }
    }
}