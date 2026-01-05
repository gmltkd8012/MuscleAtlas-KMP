package com.rebuilding.muscleatlas.login.viewmodel

import com.rebuilding.muscleatlas.ui.base.StateViewModel
import com.rebuilding.muscleatlas.ui.util.Logger
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.Kakao
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class LoginViewModel(
    private val supabaseClient: SupabaseClient,
) : StateViewModel<LoginState, LoginSideEffect>(LoginState()) {

    init {
        observeSessionStatus()
    }

    /**
     * OAuth 로그인 후 세션 상태 변경 감지
     */
    private fun observeSessionStatus() {
        supabaseClient.auth.sessionStatus
            .drop(1) // 초기 상태 무시
            .onEach { status ->
                Logger.d("LoginViewModel", "SessionStatus changed: $status")
                when (status) {
                    is SessionStatus.Authenticated -> {
                        reduceState { copy(isLoading = false) }
                        sendSideEffect(LoginSideEffect.NavigateToMain)
                    }
                    is SessionStatus.NotAuthenticated -> {
                        reduceState { copy(isLoading = false) }
                    }
                    else -> Unit
                }
            }
            .launchIn(this)
    }

    /**
     * Google 로그인 (OAuth 방식)
     */
    fun signInWithGoogle() {
        launch {
            try {
                reduceState { copy(isLoading = true) }
                Logger.d("LoginViewModel", "Starting Google sign in...")
                supabaseClient.auth.signInWith(Google)
            } catch (e: Exception) {
                Logger.e("LoginViewModel", "Google sign in failed: ${e.message}")
                reduceState { copy(isLoading = false, error = e.message) }
            }
        }
    }

    /**
     * Kakao 로그인 (OAuth 방식)
     */
    fun signInWithKakao() {
        launch {
            try {
                reduceState { copy(isLoading = true) }
                Logger.d("LoginViewModel", "Starting Kakao sign in...")
                supabaseClient.auth.signInWith(Kakao)
            } catch (e: Exception) {
                Logger.e("LoginViewModel", "Kakao sign in failed: ${e.message}")
                reduceState { copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun clearError() {
        launch {
            reduceState { copy(error = null) }
        }
    }
}

data class LoginState(
    val isLoading: Boolean = false,
    val error: String? = null,
)

sealed interface LoginSideEffect {
    data object NavigateToMain : LoginSideEffect
}
