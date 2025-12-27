package com.rebuilding.muscleatlas.member.di

import com.rebuilding.muscleatlas.member.viewmodel.MemberViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val memberModule =
    module {
        viewModelOf(::MemberViewModel)
    }
