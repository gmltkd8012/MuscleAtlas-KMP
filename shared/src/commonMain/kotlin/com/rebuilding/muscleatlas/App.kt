package com.rebuilding.muscleatlas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.splash.di.splashModule
import com.rebuilding.muscleatlas.supabase.di.supabaseModule
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.KoinApplication
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

@Composable
fun App() {
    val supabaseClient: SupabaseClient = koinInject()
    val sessionStatus by supabaseClient.auth.sessionStatus.collectAsState()
    val scope = rememberCoroutineScope()

    // Google 로그인 액션
    val googleSignInAction = supabaseClient.composeAuth.rememberSignInWithGoogle(
        onResult = { result ->
            when (result) {
                is NativeSignInResult.Success -> {
                    // 로그인 성공 - 자동으로 세션이 업데이트됨
                }

                is NativeSignInResult.ClosedByUser -> {
                    // 사용자가 취소함
                }

                is NativeSignInResult.Error -> {
                    // 오류 발생
                }

                is NativeSignInResult.NetworkError -> {
                    // 네트워크 오류
                }
            }
        }
    )

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    // 현재 상태 표시
                    Text(
                        text = when (sessionStatus) {
                            is SessionStatus.Authenticated -> "로그인됨: ${(sessionStatus as SessionStatus.Authenticated).session.user?.email ?: "Unknown"}"
                            is SessionStatus.NotAuthenticated -> "로그인이 필요합니다"
                            is SessionStatus.Initializing -> "초기화 중..."
                            is SessionStatus.RefreshFailure -> "세션 갱신 실패"
                        },
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Google 로그인/로그아웃 버튼
                    when (sessionStatus) {
                        is SessionStatus.Authenticated -> {
                            Button(
                                onClick = {
                                    scope.launch {
                                        supabaseClient.auth.signOut()
                                    }
                                }
                            ) {
                                Text("로그아웃")
                            }
                        }

                        else -> {
                            OutlinedButton(
                                onClick = { googleSignInAction.startFlow() }
                            ) {
                                Text("Google로 로그인")
                            }
                        }
                    }
                }
            }
        }
    }
}

fun muscleAtlasAppDeclaration(
    platformDeclaration: KoinApplication.() -> Unit = {},
): KoinAppDeclaration = {

    /* Feature 모듈 의존성 */
    modules(
        splashModule
    )

    modules(supabaseModule)
    platformDeclaration()
}
