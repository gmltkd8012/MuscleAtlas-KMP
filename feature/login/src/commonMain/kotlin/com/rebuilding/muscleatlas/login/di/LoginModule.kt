package com.rebuilding.muscleatlas.login.di

import com.rebuilding.muscleatlas.login.viewmodel.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val loginModule =
    module {
        viewModelOf(::LoginViewModel)
    }
