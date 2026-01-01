package com.rebuilding.muscleatlas.workout.viewmodel

import com.rebuilding.muscleatlas.data.model.Exercise
import com.rebuilding.muscleatlas.data.model.ExerciseDetail
import com.rebuilding.muscleatlas.data.repository.ExerciseRepository
import com.rebuilding.muscleatlas.ui.base.StateViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class WorkoutDetailViewModel(
    private val exerciseRepository: ExerciseRepository,
) : StateViewModel<WorkoutDetailState, WorkoutDetailSideEffect>(WorkoutDetailState()) {

    fun loadExerciseDetail(exerciseId: String) {
        launch {
            // 운동 정보 조회
            val exercise = exerciseRepository.getExercise(exerciseId)
            reduceState { copy(exercise = exercise) }

            // 상세 정보 조회
            exerciseRepository.getExerciseDetails(exerciseId)
                .onStart {
                    reduceState { copy(isLoading = true) }
                }
                .catch { e ->
                    reduceState { copy(isLoading = false, error = e.message) }
                }
                .collect { details ->
                    // movement_type, contraction_type 순서로 그룹핑
                    val groupedDetails = details.groupBy { it.movementType }
                        .mapValues { (_, items) ->
                            items.groupBy { it.contractionType }
                        }
                    reduceState {
                        copy(
                            isLoading = false,
                            details = details,
                            groupedDetails = groupedDetails,
                            error = null,
                        )
                    }
                }
        }
    }
}

data class WorkoutDetailState(
    val isLoading: Boolean = false,
    val exercise: Exercise? = null,
    val details: List<ExerciseDetail> = emptyList(),
    val groupedDetails: Map<String, Map<String, List<ExerciseDetail>>> = emptyMap(),
    val error: String? = null,
)

sealed interface WorkoutDetailSideEffect
