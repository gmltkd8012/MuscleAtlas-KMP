package com.rebuilding.muscleatlas.login.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rebuilding.muscleatlas.login.component.SignInButton
import com.rebuilding.muscleatlas.login.viewmodel.LoginViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import io.ktor.util.Platform
import org.koin.compose.koinInject

@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val supabaseClient = koinInject<SupabaseClient>()

    val onNativeSignInResult: (NativeSignInResult) -> Unit = { result ->
        when (result) {
            NativeSignInResult.Success -> {}
            else -> {

            }
        }
    }
    val googleSignInAction =
        supabaseClient.composeAuth.rememberSignInWithGoogle(onNativeSignInResult)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center,
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SignInButton(
                onClick = {
                    googleSignInAction.startFlow()
                },
                oAuthProvider = Google,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
