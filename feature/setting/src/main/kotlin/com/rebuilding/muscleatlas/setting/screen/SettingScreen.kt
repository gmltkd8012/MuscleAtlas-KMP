package com.rebuilding.muscleatlas.setting.screen

import android.R.attr.top
import android.content.Intent
import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.model.SettingScreen
import com.rebuilding.muscleatlas.setting.SettingActivity
import com.rebuilding.muscleatlas.setting.component.SettingTopBar
import com.rebuilding.muscleatlas.ui.extension.startActivity

@Composable
fun SettingScreen(
    destination: String,
    onClickBack: () -> Unit = {},
) {
    val navController = rememberNavController()
    var headerTitle by remember { mutableStateOf<String>("") }

    var isAddWorkoutScreen by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.background(AppColors.primary),
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
                    onClickEditWorkout = {
                        navController.navigate(SettingScreen.AddWorkout.route)
                    },
                    onClickAddWorkout = {
                        navController.navigate(SettingScreen.AddWorkout.route)
                    }
                )
            }
            composable(SettingScreen.AppInfo.route) {
                headerTitle = SettingScreen.AppInfo.label
                AppInfoScreen()
            }
            composable(SettingScreen.AddWorkout.route) {
                headerTitle = SettingScreen.AddWorkout.label
                isAddWorkoutScreen = true
                AddWorkoutScreen(
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