package com.rebuilding.muscleatlas.login.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rebuilding.muscleatlas.login.component.SignInButton
import com.rebuilding.muscleatlas.login.viewmodel.LoginViewModel
import com.rebuilding.muscleatlas.ui.util.Logger
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.Kakao
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import io.ktor.util.Platform
import kotlinx.coroutines.launch
import muscleatlas.core.design_system.generated.resources.Res
import muscleatlas.core.design_system.generated.resources.app_icon_dark
import muscleatlas.core.design_system.generated.resources.app_icon_light
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateToMain: () -> Unit,
    isDarkTheme: Boolean = isSystemInDarkTheme(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val appIcon =
        if (isDarkTheme)
            painterResource(Res.drawable.app_icon_dark)
        else
            painterResource(Res.drawable.app_icon_light)
    
    val scope = rememberCoroutineScope()
    val supabaseClient = koinInject<SupabaseClient>()
    
    val onNativeSignInResult: (NativeSignInResult) -> Unit = { result ->

        Logger.d("LoginScreen", "onNativeSignInResult: $result")
        when (result) {
            NativeSignInResult.Success -> {
                onNavigateToMain()
            }
            else -> {

            }
        }
    }

    val googleSignInAction =
        supabaseClient.composeAuth.rememberSignInWithGoogle(onNativeSignInResult)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = appIcon,
                contentDescription = null,
                modifier = Modifier.size(256.dp),
            )

            Spacer(Modifier.height(16.dp))

            SignInButton(
                onClick = {
                    googleSignInAction.startFlow()
                },
                oAuthProvider = Google,
                modifier = Modifier.fillMaxWidth(),
            )
            
            Spacer(Modifier.height(8.dp))

            SignInButton(
                onClick = {
                    scope.launch { 
                        supabaseClient.auth.signInWith(Kakao)
                    }
                },
                oAuthProvider = Kakao,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
