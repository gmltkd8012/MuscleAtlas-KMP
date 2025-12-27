package com.rebuilding.muscleatlas.viewmodel

import com.rebuilding.muscleatlas.ui.base.StateViewModel

class AppViewModel(

): StateViewModel<AppState, Nothing>(AppState()) {
}


data class AppState(
    val isLoggedIn: Boolean = false,
)