package com.rebuilding.muscleatlas.splash.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rebuilding.muscleatlas.splash.viewmodel.SplashSideEffect
import com.rebuilding.muscleatlas.splash.viewmodel.SplashViewModel
import muscleatlas.core.design_system.generated.resources.Res
import muscleatlas.core.design_system.generated.resources.app_icon_dark
import muscleatlas.core.design_system.generated.resources.app_icon_light
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToMain: () -> Unit,
) {
    val isDarkTheme = isSystemInDarkTheme()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SplashSideEffect.NavigateToLogin -> onNavigateToLogin()
            is SplashSideEffect.NavigateToMain -> onNavigateToMain()
        }
    }

    val appIcon = if (isDarkTheme) {
        painterResource(Res.drawable.app_icon_dark)
    } else {
        painterResource(Res.drawable.app_icon_light)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = appIcon,
            contentDescription = "App Icon",
            modifier = Modifier.size(256.dp),
        )
    }
}
