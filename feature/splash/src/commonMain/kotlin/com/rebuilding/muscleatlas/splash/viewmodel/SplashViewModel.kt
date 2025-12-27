package com.rebuilding.muscleatlas.splash.viewmodel

import com.rebuilding.muscleatlas.ui.base.StateViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SplashViewModel(
    private val supabaseClient: SupabaseClient,
) : StateViewModel<SplashState, SplashSideEffect>(SplashState()) {

    init {
        observeSessionStatus()
    }

    private fun observeSessionStatus() {
        supabaseClient.auth.sessionStatus
            .onEach { status ->
                when (status) {
                    is SessionStatus.Authenticated -> {
                        // 로그인 이력 있음 → Main으로 (추후 데이터 로딩 후)
                        launch { sendSideEffect(SplashSideEffect.NavigateToMain) }
                    }

                    is SessionStatus.NotAuthenticated -> {
                        // 로그인 이력 없음 → Login으로
                        launch { sendSideEffect(SplashSideEffect.NavigateToLogin) }
                    }

                    is SessionStatus.Initializing -> {
                        // 세션 복원 중 - 대기
                    }

                    is SessionStatus.RefreshFailure -> {
                        // 세션 갱신 실패 → Login으로
                        launch { sendSideEffect(SplashSideEffect.NavigateToLogin) }
                    }

                    else -> Unit
                }
            }
            .launchIn(this)
    }
}

data class SplashState(
    val isLoading: Boolean = true,
)

sealed interface SplashSideEffect {
    data object NavigateToLogin : SplashSideEffect
    data object NavigateToMain : SplashSideEffect
}
