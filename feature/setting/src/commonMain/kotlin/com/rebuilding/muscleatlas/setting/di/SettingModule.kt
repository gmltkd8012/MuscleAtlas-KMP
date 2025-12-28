package com.rebuilding.muscleatlas.setting.di

import com.rebuilding.muscleatlas.setting.viewmodel.SettingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingModule =
    module {
        viewModelOf(::SettingViewModel)
    }
