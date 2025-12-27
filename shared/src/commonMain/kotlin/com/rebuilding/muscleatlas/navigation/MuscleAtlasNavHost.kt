package com.rebuilding.muscleatlas.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.rebuilding.muscleatlas.login.navigation.LoginRoute
import com.rebuilding.muscleatlas.login.navigation.loginScreen
import com.rebuilding.muscleatlas.member.navigation.MemberRoute
import com.rebuilding.muscleatlas.member.navigation.memberScreen
import com.rebuilding.muscleatlas.splash.navigation.SplashRoute
import com.rebuilding.muscleatlas.splash.navigation.splashScreen
import com.rebuilding.muscleatlas.ui.navigation.Route

@Composable
fun MuscleAtlasNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: Route,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(700),
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(700),
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(700),
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(700),
            )
        },
        modifier = modifier,
    ) {
        splashScreen(
            onNavigateToLogin = {
                navController.navigate(LoginRoute) {
                    popUpTo(SplashRoute) { inclusive = true }
                }
            },
            onNavigateToMain = {
                navController.navigate(MemberRoute) {
                    popUpTo(SplashRoute) { inclusive = true }
                }
            },
        )

        loginScreen(
            onNavigateToMain = {
                navController.navigate(MemberRoute) {
                    popUpTo(SplashRoute) { inclusive = true }
                }
            },
        )

        memberScreen()
    }

}