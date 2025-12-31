package com.rebuilding.muscleatlas.app.di

import com.rebuilding.muscleatlas.app.viewmodel.AppViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule =
    module {
        viewModel { AppViewModel() }
    }