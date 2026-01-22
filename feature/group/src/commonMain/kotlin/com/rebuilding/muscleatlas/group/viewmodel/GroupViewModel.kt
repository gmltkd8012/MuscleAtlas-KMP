package com.rebuilding.muscleatlas.group.viewmodel

import com.rebuilding.muscleatlas.data.model.ExerciseGroup
import com.rebuilding.muscleatlas.data.repository.ExerciseGroupRepository
import com.rebuilding.muscleatlas.ui.base.StateViewModel
import com.rebuilding.muscleatlas.ui.util.Logger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init

class GroupViewModel(
    private val exerciseGroupRepository: ExerciseGroupRepository,
): StateViewModel<GroupState, GroupSideEffect>(GroupState()) {

    private val TAG = "GroupViewModel"

    init {
        loadExerciseGroups()
    }

    /**
     * 그룹 추가 버튼 클릭
     */
    fun onAddClick() {
        launch {
            sendSideEffect(GroupSideEffect.ShowAddGroupSheet)
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
}

data class GroupState(
    val isLoading: Boolean = false,
    val exerciseGroups: List<ExerciseGroup> = emptyList(),
)

sealed interface GroupSideEffect {
    data object ShowAddGroupSheet : GroupSideEffect
    data object HideAddGroupSheet : GroupSideEffect
}