package com.rebuilding.muscleatlas.login.viewmodel

import androidx.lifecycle.ViewModel
import com.rebuilding.muscleatlas.login.navigation.LoginRoute
import com.rebuilding.muscleatlas.ui.base.StateViewModel

internal class LoginViewModel(

) : StateViewModel<LoginState, Nothing>(LoginState()) {

}

data class LoginState(
    val isAuth: Boolean = false
)
