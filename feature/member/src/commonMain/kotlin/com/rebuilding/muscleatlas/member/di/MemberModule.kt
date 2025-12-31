package com.rebuilding.muscleatlas.member.di

import com.rebuilding.muscleatlas.data.di.dataModule
import com.rebuilding.muscleatlas.member.viewmodel.MemberViewModel
import com.rebuilding.muscleatlas.supabase.di.supabaseModule
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val memberModule =
    module {
        includes(dataModule, supabaseModule)
        viewModelOf(::MemberViewModel)
    }
