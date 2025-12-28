package com.rebuilding.muscleatlas.workout.viewmodel

import com.rebuilding.muscleatlas.ui.base.StateViewModel

class WorkoutViewModel : StateViewModel<WorkoutState, WorkoutSideEffect>(WorkoutState())

data class WorkoutState(
    val isLoading: Boolean = false,
)

sealed interface WorkoutSideEffect
