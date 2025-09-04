package com.rebuilding.muscleatlas.setting.screen

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.theme.MuscleAtlasTheme
import com.rebuilding.muscleatlas.model.AppTheme
import com.rebuilding.muscleatlas.model.SettingScreen
import com.rebuilding.muscleatlas.setting.component.SettingTopBar
import com.rebuilding.muscleatlas.setting.viewmodel.SettingViewModel

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel<SettingViewModel>(),
    isDarkTheme: Boolean,
    destination: String,
    onClickBack: () -> Unit = {},
) {
    val navController = rememberNavController()
    var headerTitle by remember { mutableStateOf<String>("") }

    var isAddWorkoutScreen by remember { mutableStateOf(false) }

    MuscleAtlasTheme(darkTheme = isDarkTheme) {
        Scaffold(
            modifier = Modifier.background(MaterialTheme.colorScheme.primary),
            topBar = {
                SettingTopBar(
                    title = headerTitle,
                    onClickBack = {
                        if (isAddWorkoutScreen) {
                            navController.navigate(SettingScreen.WorkoutManage.route)
                        } else {
                            onClickBack()
                        }
                    }
                )
            },
        ) { innerPadding ->
            NavHost(
                modifier = Modifier.padding(
                    PaddingValues(
                        top = innerPadding.calculateTopPadding()
                    )
                ),
                navController = navController,
                startDestination = destination,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
            ) {
                composable(SettingScreen.WorkoutManage.route) {
                    headerTitle = SettingScreen.WorkoutManage.label
                    isAddWorkoutScreen = false
                    WorkoutManageScreen(
                        onClickEditWorkout = { id ->
                            navController.navigate(SettingScreen.AddWorkout.createRoute(id))
                        },
                        onClickAddWorkout = {
                            navController.navigate(SettingScreen.AddWorkout.createRoute(""))
                        }
                    )
                }
                composable(SettingScreen.AppInfo.route) {
                    headerTitle = SettingScreen.AppInfo.label
                    AppInfoScreen()
                }
                composable(SettingScreen.Theme.route) {
                    headerTitle = SettingScreen.Theme.label
                    ThemeScreen(
                        onFinish = onClickBack
                    )
                }
                composable(
                    route = "${SettingScreen.AddWorkout.route}/{workoutId}",
                    arguments = listOf(navArgument("workoutId") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    })
                ) { backStackEntry ->
                    headerTitle = SettingScreen.AddWorkout.label
                    isAddWorkoutScreen = true
                    val workoutId = backStackEntry.arguments?.getString("workoutId")

                    AddWorkoutScreen(
                        workoutId = workoutId ?: "",
                        onClickBack = {
                            navController.navigate(SettingScreen.WorkoutManage.route)
                        },
                        onClickSave = {
                            navController.navigate(SettingScreen.WorkoutManage.route)
                        }
                    )
                }
            }
        }
    }
}