package com.rebuilding.muscleatlas.splash.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.rebuilding.muscleatlas.splash.screen.SplashScreen
import com.rebuilding.muscleatlas.ui.navigation.Route
import kotlinx.serialization.Serializable

@Serializable
data object SplashRoute : Route

fun NavGraphBuilder.splashScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
    onNavigateToMain: () -> Unit,
) {
    composable<SplashRoute>(
        enterTransition = { fadeIn(tween(700)) },
        exitTransition = { fadeOut(tween(700)) },
    ) {
        SplashScreen()
    }
}
