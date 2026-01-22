package com.rebuilding.muscleatlas.workout.viewmodel

import com.rebuilding.muscleatlas.data.model.Exercise
import com.rebuilding.muscleatlas.data.model.ExerciseGroup
import com.rebuilding.muscleatlas.data.repository.ExerciseGroupRepository
import com.rebuilding.muscleatlas.data.repository.ExerciseRepository
import com.rebuilding.muscleatlas.ui.base.StateViewModel
import com.rebuilding.muscleatlas.ui.util.Logger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class WorkoutViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val exerciseGroupRepository: ExerciseGroupRepository,
) : StateViewModel<WorkoutState, WorkoutSideEffect>(WorkoutState()) {

    companion object {
        private const val TAG = "WorkoutViewModel"
    }

    init {
        loadExercises()
        loadExerciseGroups()
    }

    fun loadExercises() {
        launch {
            exerciseRepository.getExercises()
                .onStart {
                    reduceState { copy(isLoading = true) }
                }
                .catch { e ->
                    Logger.e(TAG, "운동 목록 로드 실패", e)
                    reduceState { copy(isLoading = false) }
                }
                .collect { exercises ->
                    reduceState { copy(isLoading = false, exercises = exercises) }
                }
        }
    }
    
    fun loadExerciseGroups() {
        launch {
            exerciseGroupRepository.getExerciseGroups()
                .catch { e ->
                    Logger.e(TAG, "운동 그룹 목록 로드 실패", e)
                }
                .collect { groups ->
                    reduceState {
                        copy(
                            exerciseGroups = groups,
                            selectedGroupId = if (selectedGroupId.isEmpty() && groups.isNotEmpty()) {
                                groups.first().id
                            } else {
                                selectedGroupId
                            }
                        )
                    }
                }
        }
    }

    /**
     * 그룹 선택
     */
    fun selectGroup(groupId: String) {
        launch {
            reduceState { copy(selectedGroupId = groupId) }
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
                Logger.e(TAG, "운동 추가 실패", e)
                reduceState { copy(isLoading = false) }
            }
        }
    }
}

data class WorkoutState(
    val isLoading: Boolean = false,
    val exercises: List<Exercise> = emptyList(),
    val exerciseGroups: List<ExerciseGroup> = emptyList(),
    val selectedGroupId: String = "",
)

sealed interface WorkoutSideEffect {
    data object ShowAddExerciseSheet : WorkoutSideEffect
    data object HideAddExerciseSheet : WorkoutSideEffect
}
