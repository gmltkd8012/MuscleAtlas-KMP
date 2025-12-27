package com.rebuilding.muscleatlas.splash.di

import com.rebuilding.muscleatlas.splash.viewmodel.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val splashModule =
    module {
        viewModel { SplashViewModel() }
    }
