package com.rebuilding.muscleatlas.data.di

import com.rebuilding.muscleatlas.data.repository.ExerciseRepository
import com.rebuilding.muscleatlas.data.repository.ExerciseRepositoryImpl
import com.rebuilding.muscleatlas.data.repository.MemberExerciseRepository
import com.rebuilding.muscleatlas.data.repository.MemberExerciseRepositoryImpl
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
    single<ExerciseRepository> {
        ExerciseRepositoryImpl(
            supabaseClient = get(),
            ioDispatcher = get(DispatcherQualifier.Io),
        )
    }
    single<MemberExerciseRepository> {
        MemberExerciseRepositoryImpl(
            supabaseClient = get(),
            ioDispatcher = get(DispatcherQualifier.Io),
        )
    }
}
