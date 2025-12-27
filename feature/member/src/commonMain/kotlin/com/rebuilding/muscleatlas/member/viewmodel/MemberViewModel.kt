package com.rebuilding.muscleatlas.member.viewmodel

import com.rebuilding.muscleatlas.ui.base.StateViewModel

class MemberViewModel : StateViewModel<MemberState, MemberSideEffect>(MemberState())

data class MemberState(
    val isLoading: Boolean = false,
)

sealed interface MemberSideEffect
