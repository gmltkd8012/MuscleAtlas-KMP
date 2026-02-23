package com.rebuilding.muscleatlas.workout.viewmodel

import com.rebuilding.muscleatlas.data.model.Exercise
import com.rebuilding.muscleatlas.data.model.ExerciseGroup
import com.rebuilding.muscleatlas.data.model.MemberExerciseInsert
import com.rebuilding.muscleatlas.data.repository.ExerciseGroupExerciseRepository
import com.rebuilding.muscleatlas.data.repository.ExerciseGroupRepository
import com.rebuilding.muscleatlas.data.repository.ExerciseRepository
import com.rebuilding.muscleatlas.data.repository.MemberExerciseRepository
import com.rebuilding.muscleatlas.data.repository.MemberRepository
import com.rebuilding.muscleatlas.ui.base.StateViewModel
import com.rebuilding.muscleatlas.ui.util.Logger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class WorkoutViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val exerciseGroupRepository: ExerciseGroupRepository,
    private val exerciseGroupExerciseRepository: ExerciseGroupExerciseRepository,
    private val memberRepository: MemberRepository,
    private val memberExerciseRepository: MemberExerciseRepository,
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
    fun addExercise(name: String, groupId: String?) {
        launch {
            try {
                reduceState { copy(isLoading = true) }
                val newExercise = exerciseRepository.insertExercise(name)

                // 그룹이 선택된 경우, 해당 그룹에 운동 추가
                if (!groupId.isNullOrEmpty()) {
                    exerciseGroupExerciseRepository.addExercisesToGroup(
                        groupId = groupId,
                        exerciseIds = listOf(newExercise.id)
                    )
                }

                // 모든 기존 회원에게 새 운동 매핑 추가
                createMemberExercisesForNewExercise(newExercise.id)

                sendSideEffect(WorkoutSideEffect.HideAddExerciseSheet)

                // 현재 선택된 그룹의 운동 목록 다시 로드
                loadExercisesByGroup(state.value.selectedGroupId)
            } catch (e: Exception) {
                Logger.e(TAG, "운동 추가 실패", e)
                reduceState { copy(isLoading = false) }
            }
        }
    }

    /**
     * 새 운동 추가 시 모든 기존 회원에게 member_exercises 생성
     */
    private suspend fun createMemberExercisesForNewExercise(exerciseId: String) {
        try {
            // 모든 회원 조회
            val members = memberRepository.getMembers().first()

            if (members.isEmpty()) {
                Logger.d(TAG, "기존 회원이 없어 member_exercises 생성 생략")
                return
            }

            // 각 회원에 대해 member_exercises 생성 (기본값: canPerform = false)
            val memberExercises = members.map { member ->
                MemberExerciseInsert(
                    memberId = member.id,
                    exerciseId = exerciseId,
                    canPerform = false
                )
            }

            memberExerciseRepository.createMemberExercises(memberExercises)
            Logger.d(TAG, "새 운동에 대해 ${members.size}명의 회원 매핑 생성 완료")
        } catch (e: Exception) {
            Logger.e(TAG, "회원-운동 매핑 생성 실패", e)
            // 에러가 발생해도 운동 추가 자체는 성공이므로 throw하지 않음
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
