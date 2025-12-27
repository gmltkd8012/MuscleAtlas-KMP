package com.rebuilding.muscleatlas.splash.di

import com.rebuilding.muscleatlas.splash.viewmodel.SplashViewModel
import org.koin.dsl.module

val splashModule =
    module { SplashViewModel() }
