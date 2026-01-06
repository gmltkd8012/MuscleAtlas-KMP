package com.rebuilding.muscleatlas.splash.viewmodel

import com.rebuilding.muscleatlas.data.repository.SessionRepository
import com.rebuilding.muscleatlas.ui.base.StateViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SplashViewModel(
    private val sessionRepository: SessionRepository,
) : StateViewModel<SplashState, SplashSideEffect>(SplashState()) {

    init {
        initializedApp()
    }

    private fun initializedApp() {
        launch {
            when (hasSession()) {
                true -> { // 로그인 정보 있음 - 메인 페이지 이동
                    sendSideEffect(SplashSideEffect.NavigateToMain)
                }
                false -> { // 로그인 정보 없음 - 로그인 페이지 이동
                    sendSideEffect(SplashSideEffect.NavigateToLogin)
                }
            }
            reduceState { copy(isLoading = false) }
        }
    }

    private suspend fun hasSession(): Boolean =
        sessionRepository.loadSessionFromStorage().getOrDefault(false)
}

data class SplashState(
    val isLoading: Boolean = true,
)

sealed interface SplashSideEffect {
    data object NavigateToLogin : SplashSideEffect
    data object NavigateToMain : SplashSideEffect
}
