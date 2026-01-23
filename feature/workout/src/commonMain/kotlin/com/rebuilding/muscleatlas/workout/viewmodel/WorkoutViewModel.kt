package com.rebuilding.muscleatlas.workout.viewmodel

import com.rebuilding.muscleatlas.data.model.Exercise
import com.rebuilding.muscleatlas.data.model.ExerciseGroup
import com.rebuilding.muscleatlas.data.repository.ExerciseGroupExerciseRepository
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
    private val exerciseGroupExerciseRepository: ExerciseGroupExerciseRepository,
) : StateViewModel<WorkoutState, WorkoutSideEffect>(WorkoutState()) {

    companion object {
        private const val TAG = "WorkoutViewModel"
    }

    init {
        loadExerciseGroups()
    }

    /**
     * 전체 운동 목록 로드 (그룹 필터링 없음)
     */
    private fun loadAllExercises() {
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

    /**
     * 선택된 그룹의 운동 목록 로드
     */
    private fun loadExercisesByGroup(groupId: String) {
        launch {
            if (groupId.isEmpty()) {
                loadAllExercises()
            } else {
                exerciseGroupExerciseRepository.getExercisesInGroup(groupId)
                    .onStart {
                        reduceState { copy(isLoading = true) }
                    }
                    .catch { e ->
                        Logger.e(TAG, "그룹별 운동 목록 로드 실패", e)
                        reduceState { copy(isLoading = false) }
                    }
                    .collect { exercises ->
                        Logger.d(TAG, "운동 종목 로드 -> $exercises")
                        reduceState { copy(isLoading = false, exercises = exercises) }
                    }
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
                    // "전체" 그룹을 맨 앞에 추가
                    val groupsWithAll = listOf(ExerciseGroup.ALL) + groups

                    // 초기 로드 시 "전체" 선택, 이미 선택된 그룹이 있으면 유지
                    val newGroupId = if (state.value.selectedGroupId.isEmpty()) {
                        "" // "전체" 선택
                    } else {
                        state.value.selectedGroupId
                    }

                    reduceState {
                        copy(
                            exerciseGroups = groupsWithAll,
                            selectedGroupId = newGroupId
                        )
                    }

                    // 선택된 그룹의 운동 로드
                    loadExercisesByGroup(newGroupId)
                }
        }
    }

    /**
     * 그룹 선택
     */
    fun selectGroup(groupId: String) {
        launch {
            reduceState { copy(selectedGroupId = groupId) }
            loadExercisesByGroup(groupId)
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
                loadExercisesByGroup(state.value.selectedGroupId)
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
    val selectedGroupId: String = "", // 빈 문자열 = "전체" 선택
)

sealed interface WorkoutSideEffect {
    data object ShowAddExerciseSheet : WorkoutSideEffect
    data object HideAddExerciseSheet : WorkoutSideEffect
}
