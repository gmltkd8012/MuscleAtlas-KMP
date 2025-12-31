package com.rebuilding.muscleatlas.setting.viewmodel

import com.rebuilding.muscleatlas.ui.base.StateViewModel

class SettingViewModel : StateViewModel<SettingState, SettingSideEffect>(SettingState())

data class SettingState(
    val isLoading: Boolean = false,
)

sealed interface SettingSideEffect
