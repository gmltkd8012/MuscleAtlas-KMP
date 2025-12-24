package com.rebuilding.convention.plugin

import com.android.build.gradle.LibraryExtension
import com.rebuilding.convention.configureFlavorDefault
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class LibraryFlavorConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<LibraryExtension> {
                configureFlavorDefault(this)
            }
        }
    }
}