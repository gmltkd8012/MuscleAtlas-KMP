package com.rebuilding.muscleatlas.data.di

import com.rebuilding.muscleatlas.data.repository.MemberRepository
import com.rebuilding.muscleatlas.data.repository.MemberRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    includes(dispatcherModule)
    single<MemberRepository> {
        MemberRepositoryImpl(
            supabaseClient = get(),
            ioDispatcher = get(DispatcherQualifier.Io),
        )
    }
}
