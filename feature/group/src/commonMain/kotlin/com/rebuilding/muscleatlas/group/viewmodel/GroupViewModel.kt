package com.rebuilding.muscleatlas.group.viewmodel

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
import org.koin.core.KoinApplication.Companion.init

class GroupViewModel(
    private val exerciseGroupRepository: ExerciseGroupRepository,
    private val exerciseRepository: ExerciseRepository,
    private val exerciseGroupExerciseRepository: ExerciseGroupExerciseRepository,
): StateViewModel<GroupState, GroupSideEffect>(GroupState()) {

    private val TAG = "GroupViewModel"

    init {
        loadExerciseGroups()
        loadExercises()
        loadGroupExerciseCounts()
    }

    /**
     * 그룹 추가 버튼 클릭
     */
    fun onAddClick() {
        launch {
            sendSideEffect(GroupSideEffect.ShowAddGroupSheet)
        }
    }

    /**
     * 운동 선택 바텀시트 열기
     */
    fun onSelectExercisesClick(groupId: String) {
        launch {
            // 해당 그룹에 이미 등록된 운동들을 불러와서 selectedExerciseIds에 설정
            exerciseGroupExerciseRepository.getExercisesInGroup(groupId)
                .collect { exercises ->
                    val exerciseIds = exercises.map { it.id }.toSet()
                    reduceState {
                        copy(
                            selectedGroupId = groupId,
                            selectedExerciseIds = exerciseIds
                        )
                    }
                    sendSideEffect(GroupSideEffect.ShowExerciseSelectSheet)
                }
        }
    }

    /**
     * 운동 토글 (선택/해제)
     */
    fun toggleExerciseSelection(exerciseId: String) {
        launch {
            reduceState {
                val newSelection = if (selectedExerciseIds.contains(exerciseId)) {
                    selectedExerciseIds - exerciseId
                } else {
                    selectedExerciseIds + exerciseId
                }
                copy(selectedExerciseIds = newSelection)
            }
        }
    }

    private fun loadExerciseGroups() {
        launch {
            exerciseGroupRepository.getExerciseGroups()
                .onStart {
                    reduceState { copy(isLoading = true) }
                }
                .catch { e ->
                    Logger.e(TAG, "운동 그룹 목록 로드 실패", e)
                }
                .collect { groups ->
                    reduceState {
                        copy(
                            isLoading = false,
                            exerciseGroups = groups.sortedBy { it.createdAt }
                        )
                    }
                }
        }
    }

    private fun loadExercises() {
        launch {
            exerciseRepository.getExercises()
                .catch { e ->
                    Logger.e(TAG, "운동 목록 로드 실패", e)
                }
                .collect { exercises ->
                    reduceState { copy(exercises = exercises) }
                }
        }
    }

    private fun loadGroupExerciseCounts() {
        launch {
            exerciseGroupRepository.getExerciseGroups()
                .catch { e ->
                    Logger.e(TAG, "그룹별 운동 개수 로드 실패", e)
                }
                .collect { groups ->
                    val counts = mutableMapOf<String, Int>()
                    groups.forEach { group ->
                        exerciseGroupExerciseRepository.getExercisesInGroup(group.id)
                            .collect { exercises ->
                                counts[group.id] = exercises.size
                                reduceState { copy(groupExerciseCounts = counts.toMap()) }
                            }
                    }
                }
        }
    }

    /**
     * 운동 그룹 추가
     */
    internal fun addExerciseGroup(name: String) {
        launch {
            try {
                exerciseGroupRepository.insertExerciseGroup(name)
                sendSideEffect(GroupSideEffect.HideAddGroupSheet)
                // 목록 다시 로드
                loadExerciseGroups()
            } catch (e: Exception) {
                Logger.e(TAG, "운동 그룹 추가 실패", e)
            }
        }
    }

    /**
     * 선택한 운동들을 그룹에 저장
     */
    fun saveSelectedExercises() {
        launch {
            try {
                exerciseGroupExerciseRepository.addExercisesToGroup(
                    groupId = state.value.selectedGroupId,
                    exerciseIds = state.value.selectedExerciseIds.toList()
                )
                sendSideEffect(GroupSideEffect.HideExerciseSelectSheet)
                reduceState { copy(selectedExerciseIds = emptySet()) }
                // 운동 개수 다시 로드
                loadGroupExerciseCounts()
            } catch (e: Exception) {
                Logger.e(TAG, "운동 저장 실패", e)
            }
        }
    }

    /**
     * 운동 그룹 삭제
     */
    fun deleteExerciseGroup() {
        launch {
            try {
                val groupId = state.value.selectedGroupId
                if (groupId.isNotEmpty()) {
                    // 그룹에 속한 운동들 먼저 삭제
                    exerciseGroupExerciseRepository.clearGroup(groupId)
                    // 그룹 삭제
                    exerciseGroupRepository.deleteExerciseGroup(groupId)
                    sendSideEffect(GroupSideEffect.HideExerciseSelectSheet)
                    reduceState { copy(selectedExerciseIds = emptySet(), selectedGroupId = "") }
                    // 목록 다시 로드
                    loadExerciseGroups()
                    loadGroupExerciseCounts()
                }
            } catch (e: Exception) {
                Logger.e(TAG, "운동 그룹 삭제 실패", e)
            }
        }
    }
}

data class GroupState(
    val isLoading: Boolean = false,
    val exerciseGroups: List<ExerciseGroup> = emptyList(),
    val exercises: List<Exercise> = emptyList(),
    val selectedGroupId: String = "",
    val selectedExerciseIds: Set<String> = emptySet(),
    val groupExerciseCounts: Map<String, Int> = emptyMap(),
)

sealed interface GroupSideEffect {
    data object ShowAddGroupSheet : GroupSideEffect
    data object HideAddGroupSheet : GroupSideEffect
    data object ShowExerciseSelectSheet : GroupSideEffect
    data object HideExerciseSelectSheet : GroupSideEffect
}