package com.rebuilding.muscleatlas.workout.viewmodel

import com.rebuilding.muscleatlas.data.model.Exercise
import com.rebuilding.muscleatlas.data.repository.ExerciseRepository
import com.rebuilding.muscleatlas.ui.base.StateViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class WorkoutViewModel(
    private val exerciseRepository: ExerciseRepository,
) : StateViewModel<WorkoutState, WorkoutSideEffect>(WorkoutState()) {

    init {
        loadExercises()
    }

    fun loadExercises() {
        launch {
            exerciseRepository.getExercises()
                .onStart {
                    reduceState { copy(isLoading = true) }
                }
                .catch { e ->
                    reduceState { copy(isLoading = false, error = e.message) }
                }
                .collect { exercises ->
                    reduceState { copy(isLoading = false, exercises = exercises, error = null) }
                }
        }
    }
}

data class WorkoutState(
    val isLoading: Boolean = false,
    val exercises: List<Exercise> = emptyList(),
    val error: String? = null,
)

sealed interface WorkoutSideEffect
