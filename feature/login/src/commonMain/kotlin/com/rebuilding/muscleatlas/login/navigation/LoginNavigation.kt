package com.rebuilding.muscleatlas.login.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rebuilding.muscleatlas.login.screen.LoginScreen
import com.rebuilding.muscleatlas.ui.navigation.Route
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object LoginRoute : Route

fun NavGraphBuilder.loginScreen(
    onNavigateToMain: () -> Unit,
) {
    composable<LoginRoute>(
        enterTransition = { fadeIn(tween(700)) },
        exitTransition = { fadeOut(tween(700)) },
    ) {
        LoginScreen(
            viewModel = koinViewModel(),
            onNavigateToMain = onNavigateToMain,
        )
    }
}
