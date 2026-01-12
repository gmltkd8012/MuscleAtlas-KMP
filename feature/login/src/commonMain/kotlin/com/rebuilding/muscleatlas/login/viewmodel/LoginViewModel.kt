package com.rebuilding.muscleatlas.login.viewmodel

import com.rebuilding.muscleatlas.ui.base.StateViewModel
import com.rebuilding.muscleatlas.ui.util.Logger
import com.rebuilding.muscleatlas.util.Platform
import com.rebuilding.muscleatlas.util.platform
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Apple
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.Kakao
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class LoginViewModel(
    private val supabaseClient: SupabaseClient,
) : StateViewModel<LoginState, LoginSideEffect>(LoginState(platform = platform)) {

    init {
        Logger.d("LoginViewModel", "Platform: $platform")
        observeSessionStatus()
    }

    /**
     * OAuth 로그인 후 세션 상태 변경 감지
     * 로그인 성공 시, 메인 화면으로 이동
     */
    private fun observeSessionStatus() {
        supabaseClient.auth.sessionStatus
            .onEach { status ->
                Logger.d("LoginViewModel", "SessionStatus: $status")
                when (status) {
                    is SessionStatus.Authenticated -> sendSideEffect(LoginSideEffect.NavigateToMain)
                    else -> Unit
                }
            }
            .launchIn(this)
    }

    /**
     * Google 로그인 (OAuth 방식)
     */
    internal fun signInWithGoogle() {
        launch {
            try {
                Logger.d("LoginViewModel", "Starting Google sign in...")
                supabaseClient.auth.signInWith(Google)
            } catch (e: Exception) {
                Logger.e("LoginViewModel", "Google sign in failed", e)
            }
        }
    }

    /**
     * Apple 로그인 (OAuth 방식)
     */
    internal fun signInWithApple() {
        launch {
            try {
                Logger.d("LoginViewModel", "Starting Apple sign in...")
                supabaseClient.auth.signInWith(Apple)
            } catch (e: Exception) {
                Logger.e("LoginViewModel", "Apple sign in failed", e)
            }
        }
    }

    /**
     * Kakao 로그인 (OAuth 방식)
     */
    internal fun signInWithKakao() {
        launch {
            try {
                Logger.d("LoginViewModel", "Starting Kakao sign in...")
                supabaseClient.auth.signInWith(Kakao)
            } catch (e: Exception) {
                Logger.e("LoginViewModel", "Kakao sign in failed", e)
            }
        }
    }
}

data class LoginState(
    val platform: Platform = Platform.DEFAULT,
)

sealed interface LoginSideEffect {
    data object NavigateToMain : LoginSideEffect
}
