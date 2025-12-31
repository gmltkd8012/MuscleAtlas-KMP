package com.rebuilding.muscleatlas.member.viewmodel

import com.rebuilding.muscleatlas.data.model.CreateMemberRequest
import com.rebuilding.muscleatlas.data.model.Member
import com.rebuilding.muscleatlas.data.repository.MemberRepository
import com.rebuilding.muscleatlas.ui.base.StateViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MemberViewModel(
    private val supabaseClient: SupabaseClient,
    private val memberRepository: MemberRepository,
) : StateViewModel<MemberState, MemberSideEffect>(MemberState()) {

    init {
        loadMembers()
    }

    fun loadMembers() {
        launch {
            memberRepository.getMembers()
                .onStart {
                    reduceState { copy(isLoading = true) }
                }
                .catch { e ->
                    reduceState { copy(isLoading = false, error = e.message) }
                }
                .collect { members ->
                    reduceState { copy(isLoading = false, members = members, error = null) }
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

            if (userId != null) {
                try {
                    val request = CreateMemberRequest(
                        name = name,
                        memo = memo,
                        userId = userId,
                    )

                    memberRepository.createMember(request)
                    sendSideEffect(MemberSideEffect.HideAddMemberSheet)
                    loadMembers() // 목록 새로고침
                } catch (e: Exception) {
                    reduceState { copy(isLoading = false, error = e.message) }
                }
            }
        }
    }

    fun onDeleteMember(id: String) {
        launch {
            try {
                memberRepository.deleteMember(id)
                loadMembers()
            } catch (e: Exception) {
                reduceState { copy(error = e.message) }
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
    val error: String? = null,
)

sealed interface MemberSideEffect {
    data object ShowAddMemberSheet : MemberSideEffect
    data object HideAddMemberSheet : MemberSideEffect
}
