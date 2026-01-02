package com.rebuilding.muscleatlas.app.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rebuilding.muscleatlas.login.navigation.LoginRoute
import com.rebuilding.muscleatlas.login.navigation.loginScreen
import com.rebuilding.muscleatlas.member.navigation.MemberDetailRoute
import com.rebuilding.muscleatlas.member.navigation.memberDetailScreen
import com.rebuilding.muscleatlas.setting.navigation.AccountRoute
import com.rebuilding.muscleatlas.setting.navigation.accountScreen
import com.rebuilding.muscleatlas.splash.navigation.SplashRoute
import com.rebuilding.muscleatlas.splash.navigation.splashScreen
import com.rebuilding.muscleatlas.ui.navigation.Route
import com.rebuilding.muscleatlas.workout.navigation.WorkoutDetailRoute
import com.rebuilding.muscleatlas.workout.navigation.workoutDetailScreen

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
        // Auth Flow (전체 화면, Bottom Navigation 없음)
        splashScreen(
            onNavigateToLogin = {
                navController.navigate(LoginRoute) {
                    popUpTo(SplashRoute) { inclusive = true }
                }
            },
            onNavigateToMain = {
                navController.navigate(MainRoute) {
                    popUpTo(SplashRoute) { inclusive = true }
                }
            },
        )

        loginScreen(
            onNavigateToMain = {
                navController.navigate(MainRoute) {
                    popUpTo(LoginRoute) { inclusive = true }
                }
            },
        )

        // Main Screen (Scaffold + Bottom Navigation)
        composable<MainRoute> {
            MainScreen(
                onNavigateToAccount = {
                    navController.navigate(AccountRoute)
                },
                onNavigateToWorkoutDetail = { exerciseId ->
                    navController.navigate(WorkoutDetailRoute(exerciseId))
                },
                onNavigateToMemberDetail = { memberId ->
                    navController.navigate(MemberDetailRoute(memberId))
                }
            )
        }

        // ================ 2Depth Screens ================ //

        // Workout Detail
        workoutDetailScreen(
            onNavigateBack = {
                navController.popBackStack()
            }
        )

        // Memeber Detail
        memberDetailScreen(
            onNavigateBack = {
                navController.popBackStack()
            },
            onNavigateToWorkoutDetail = { exerciseId ->
                navController.navigate(WorkoutDetailRoute(exerciseId, false))
            }
        )

        // Setting Detail - Account
        accountScreen(
            onNavigateBack = {
                navController.popBackStack()
            },
            onLogout = {
                navController.navigate(LoginRoute) {
                    popUpTo(MainRoute) { inclusive = true }
                }
            },
        )
    }
}