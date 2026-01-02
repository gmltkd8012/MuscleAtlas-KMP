package com.rebuilding.muscleatlas.member.viewmodel

import com.rebuilding.muscleatlas.data.model.Exercise
import com.rebuilding.muscleatlas.data.model.Member
import com.rebuilding.muscleatlas.data.model.MemberExercise
import com.rebuilding.muscleatlas.data.model.MemberExerciseUpdate
import com.rebuilding.muscleatlas.data.model.UpdateMemberRequest
import com.rebuilding.muscleatlas.data.repository.ExerciseRepository
import com.rebuilding.muscleatlas.data.repository.MemberExerciseRepository
import com.rebuilding.muscleatlas.data.repository.MemberRepository
import com.rebuilding.muscleatlas.ui.base.StateViewModel
import com.rebuilding.muscleatlas.util.DateFormatter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MemberDetailViewModel(
    private val memberRepository: MemberRepository,
    private val memberExerciseRepository: MemberExerciseRepository,
    private val exerciseRepository: ExerciseRepository,
) : StateViewModel<MemberDetailState, MemberDetailSideEffect>(MemberDetailState()) {

    fun loadMemberDetail(memberId: String) {
        launch {
            reduceState { copy(isLoading = true) }
            
            try {
                // 1. 회원 정보 로드
                val member = memberRepository.getMember(memberId)
                
                // 2. 회원의 운동 목록 로드
                val memberExercises = memberExerciseRepository.getMemberExercises(memberId).first()
                
                // 3. 모든 운동 정보 로드 (이름을 가져오기 위해)
                val exercises = exerciseRepository.getExercises().first()
                val exerciseMap = exercises.associateBy { it.id }
                
                // 4. MemberExercise + Exercise 정보 결합
                val exerciseItems = memberExercises.mapNotNull { memberExercise ->
                    exerciseMap[memberExercise.exerciseId]?.let { exercise ->
                        MemberExerciseItem(
                            memberExercise = memberExercise,
                            exercise = exercise,
                        )
                    }
                }
                
                reduceState {
                    copy(
                        isLoading = false,
                        member = member,
                        exerciseItems = exerciseItems,
                        error = null,
                    )
                }
            } catch (e: Exception) {
                reduceState {
                    copy(
                        isLoading = false,
                        error = e.message,
                    )
                }
            }
        }
    }
    
    fun updateExerciseCanPerform(memberExerciseId: String, canPerform: Boolean) {
        launch {
            try {
                memberExerciseRepository.updateMemberExercise(
                    id = memberExerciseId,
                    update = MemberExerciseUpdate(canPerform = canPerform),
                )
                
                // 로컬 상태 업데이트
                reduceState {
                    copy(
                        exerciseItems = exerciseItems.map { item ->
                            if (item.memberExercise.id == memberExerciseId) {
                                item.copy(
                                    memberExercise = item.memberExercise.copy(canPerform = canPerform)
                                )
                            } else {
                                item
                            }
                        }
                    )
                }
            } catch (e: Exception) {
                reduceState { copy(error = e.message) }
            }
        }
    }
    
    /**
     * 회원 메모 업데이트
     */
    fun updateMemo(memberId: String, memberName: String, newMemo: String) {
        launch {
            try {
                val currentTimeMillis = DateFormatter.getCurrentTimeMillis()
                
                val updatedMember = memberRepository.updateMember(
                    id = memberId,
                    request = UpdateMemberRequest(
                        name = memberName,
                        memo = newMemo,
                        updatedAt = currentTimeMillis,
                    )
                )
                
                // 로컬 상태 업데이트
                reduceState {
                    copy(member = updatedMember)
                }
            } catch (e: Exception) {
                reduceState { copy(error = e.message) }
            }
        }
    }
}

/**
 * 회원 운동 항목 (MemberExercise + Exercise 정보 결합)
 */
data class MemberExerciseItem(
    val memberExercise: MemberExercise,
    val exercise: Exercise,
)

data class MemberDetailState(
    val isLoading: Boolean = false,
    val member: Member? = null,
    val exerciseItems: List<MemberExerciseItem> = emptyList(),
    val error: String? = null,
)

sealed interface MemberDetailSideEffect
