package com.rebuilding.muscleatlas.group.di

import com.rebuilding.muscleatlas.data.di.dataModule
import com.rebuilding.muscleatlas.group.viewmodel.GroupViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val groupModule =
    module {
        includes(dataModule)
        viewModelOf(::GroupViewModel)
    }