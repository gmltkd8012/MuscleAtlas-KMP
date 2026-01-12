package com.rebuilding.muscleatlas.ui.di

import com.rebuilding.muscleatlas.ui.util.CrashReporter
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun platformCrashReporterModule(): Module

val uiModule = module {
    includes(platformCrashReporterModule())
}
