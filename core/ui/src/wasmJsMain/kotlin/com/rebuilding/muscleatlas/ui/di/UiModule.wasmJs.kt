package com.rebuilding.muscleatlas.ui.di

import com.rebuilding.muscleatlas.ui.util.CrashReporter
import com.rebuilding.muscleatlas.ui.util.WasmJsCrashReporter
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformCrashReporterModule(): Module = module {
    single<CrashReporter> { WasmJsCrashReporter() }
}
