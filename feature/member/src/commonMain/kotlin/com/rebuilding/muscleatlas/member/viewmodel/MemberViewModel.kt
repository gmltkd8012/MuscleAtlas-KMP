package com.rebuilding.muscleatlas.member.viewmodel

import com.rebuilding.muscleatlas.ui.base.StateViewModel
import kotlinx.coroutines.launch

class MemberViewModel : StateViewModel<MemberState, MemberSideEffect>(MemberState()) {

    fun onAddMemberClick() {
        launch {
            sendSideEffect(MemberSideEffect.ShowAddMemberSheet)
        }
    }

    fun onAddMember(name: String, memo: String) {
        launch {
            // TODO: 실제 회원 추가 로직
            sendSideEffect(MemberSideEffect.HideAddMemberSheet)
        }
    }
}

data class MemberState(
    val isLoading: Boolean = false,
)

sealed interface MemberSideEffect {
    data object ShowAddMemberSheet : MemberSideEffect
    data object HideAddMemberSheet : MemberSideEffect
}
