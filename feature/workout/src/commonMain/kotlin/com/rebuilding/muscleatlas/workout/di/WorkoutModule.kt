package com.rebuilding.muscleatlas.workout.di

import com.rebuilding.muscleatlas.data.di.dataModule
import com.rebuilding.muscleatlas.workout.viewmodel.WorkoutDetailViewModel
import com.rebuilding.muscleatlas.workout.viewmodel.WorkoutViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val workoutModule =
    module {
        includes(dataModule)
        viewModelOf(::WorkoutViewModel)
        viewModel {
            WorkoutDetailViewModel(
                exerciseRepository = get(),
                movementMechanicRepository = get(),
                storageRepository = get()
            )
        }
    }
