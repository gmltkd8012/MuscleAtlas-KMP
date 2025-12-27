package com.rebuilding.muscleatlas.login.di

import com.rebuilding.muscleatlas.login.viewmodel.LoginViewModel
import org.koin.dsl.module

val loginModule =
    module { LoginViewModel() }
