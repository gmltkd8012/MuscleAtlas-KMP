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
    
    /**
     * 운동 추가 버튼 클릭
     */
    fun onAddExerciseClick() {
        launch {
            sendSideEffect(WorkoutSideEffect.ShowAddExerciseSheet)
        }
    }
    
    /**
     * 운동 추가
     */
    fun addExercise(name: String) {
        launch {
            try {
                reduceState { copy(isLoading = true) }
                exerciseRepository.insertExercise(name)
                sendSideEffect(WorkoutSideEffect.HideAddExerciseSheet)
                // 목록 다시 로드
                loadExercises()
            } catch (e: Exception) {
                reduceState { copy(isLoading = false, error = e.message) }
            }
        }
    }
}

data class WorkoutState(
    val isLoading: Boolean = false,
    val exercises: List<Exercise> = emptyList(),
    val error: String? = null,
)

sealed interface WorkoutSideEffect {
    data object ShowAddExerciseSheet : WorkoutSideEffect
    data object HideAddExerciseSheet : WorkoutSideEffect
}
