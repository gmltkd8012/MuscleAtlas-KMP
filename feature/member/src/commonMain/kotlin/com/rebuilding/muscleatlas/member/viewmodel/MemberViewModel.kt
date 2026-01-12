package com.rebuilding.muscleatlas.member.viewmodel

import com.rebuilding.muscleatlas.data.model.CreateMemberRequest
import com.rebuilding.muscleatlas.data.model.Member
import com.rebuilding.muscleatlas.data.model.MemberExerciseInsert
import com.rebuilding.muscleatlas.data.repository.ExerciseRepository
import com.rebuilding.muscleatlas.data.repository.MemberExerciseRepository
import com.rebuilding.muscleatlas.data.repository.MemberRepository
import com.rebuilding.muscleatlas.ui.base.StateViewModel
import com.rebuilding.muscleatlas.ui.util.Logger
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


class MemberViewModel(
    private val supabaseClient: SupabaseClient,
    private val memberRepository: MemberRepository,
    private val exerciseRepository: ExerciseRepository,
    private val memberExerciseRepository: MemberExerciseRepository,
) : StateViewModel<MemberState, MemberSideEffect>(MemberState()) {

    companion object {
        private const val TAG = "MemberViewModel"
    }

    internal fun loadMembers() {
        launch {
            memberRepository.getMembers()
                .onStart {
                    reduceState { copy(isLoading = true) }
                }
                .catch { e ->
                    Logger.e(TAG, "회원 목록 로드 실패", e)
                    reduceState { copy(isLoading = false) }
                }
                .collect { members ->
                    reduceState { copy(isLoading = false, members = members) }
                }
        }
    }

    fun onAddMemberClick() {
        launch {
            sendSideEffect(MemberSideEffect.ShowAddMemberSheet)
        }
    }

    fun onAddMember(name: String, memo: String) {
        launch {
            reduceState { copy(isLoading = true) }

            val userId = getUserId()

            if (userId == null) {
                Logger.e(TAG, "회원 추가 실패: 로그인이 필요합니다", null)
                reduceState { copy(isLoading = false) }
                return@launch
            }

            try {
                val request = CreateMemberRequest(
                    name = name,
                    memo = memo,
                    userId = userId,
                )
                val newMember = memberRepository.createMember(request)
                val exercises = exerciseRepository.getExercises().first()

                val memberExercises = exercises.map { exercise ->
                    MemberExerciseInsert(
                        memberId = newMember.id,
                        exerciseId = exercise.id,
                        canPerform = false,
                    )
                }

                memberExerciseRepository.createMemberExercises(memberExercises)

                sendSideEffect(MemberSideEffect.HideAddMemberSheet)
                loadMembers() // 목록 새로고침
            } catch (e: Exception) {
                Logger.e(TAG, "회원 추가 실패", e)
                reduceState { copy(isLoading = false) }
            }
        }
    }

    // Supabase 의 id
    private suspend fun getUserId(): String? =
        supabaseClient.auth.currentUserOrNull()?.id
}

data class MemberState(
    val isLoading: Boolean = false,
    val members: List<Member> = emptyList(),
)

sealed interface MemberSideEffect {
    data object ShowAddMemberSheet : MemberSideEffect
    data object HideAddMemberSheet : MemberSideEffect
}
