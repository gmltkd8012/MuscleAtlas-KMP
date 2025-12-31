package com.rebuilding.muscleatlas.workout.di

import com.rebuilding.muscleatlas.workout.viewmodel.WorkoutViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val workoutModule =
    module {
        viewModelOf(::WorkoutViewModel)
    }
