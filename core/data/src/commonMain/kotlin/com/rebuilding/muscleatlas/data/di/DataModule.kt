package com.rebuilding.muscleatlas.data.di

import com.rebuilding.muscleatlas.data.repository.ExerciseGroupExerciseRepository
import com.rebuilding.muscleatlas.data.repository.ExerciseGroupExerciseRepositoryImpl
import com.rebuilding.muscleatlas.data.repository.ExerciseGroupRepository
import com.rebuilding.muscleatlas.data.repository.ExerciseGroupRepositoryImpl
import com.rebuilding.muscleatlas.data.repository.ExerciseRepository
import com.rebuilding.muscleatlas.data.repository.ExerciseRepositoryImpl
import com.rebuilding.muscleatlas.data.repository.MemberExerciseRepository
import com.rebuilding.muscleatlas.data.repository.MemberExerciseRepositoryImpl
import com.rebuilding.muscleatlas.data.repository.MemberInviteRepository
import com.rebuilding.muscleatlas.data.repository.MemberInviteRepositoryImpl
import com.rebuilding.muscleatlas.data.repository.MemberRepository
import com.rebuilding.muscleatlas.data.repository.MemberRepositoryImpl
import com.rebuilding.muscleatlas.data.repository.SessionRepository
import com.rebuilding.muscleatlas.data.repository.SessionRepositoryImpl
import com.rebuilding.muscleatlas.data.repository.StorageRepository
import com.rebuilding.muscleatlas.data.repository.StorageRepositoryImpl
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
    single<ExerciseGroupRepository> {
        ExerciseGroupRepositoryImpl(
            supabaseClient = get(),
            ioDispatcher = get(DispatcherQualifier.Io),
        )
    }
    single<ExerciseGroupExerciseRepository> {
        ExerciseGroupExerciseRepositoryImpl(
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
    single<MemberInviteRepository> {
        MemberInviteRepositoryImpl(
            supabaseClient = get(),
            ioDispatcher = get(DispatcherQualifier.Io),
        )
    }
    single<SessionRepository> {
        SessionRepositoryImpl(
            sessionDataSource = get(),
            ioDispatcher = get(DispatcherQualifier.Io),
        )
    }
    single<StorageRepository> {
        StorageRepositoryImpl(
            supabaseClient = get(),
            ioDispatcher = get(DispatcherQualifier.Io),
        )
    }
}
