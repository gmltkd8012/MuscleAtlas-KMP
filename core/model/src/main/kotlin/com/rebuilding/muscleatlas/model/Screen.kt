package com.rebuilding.muscleatlas.model

import android.R.attr.data
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    data object Client: Screen(
        route = "client",
        label = "회원 관리",
        icon = Icons.Default.AccountCircle
    )

    data object Setting: Screen(
        route = "setting",
        label = "설정",
        icon = Icons.Default.Settings
    )

    companion object {
        val allScreens by lazy { listOf(Client, Setting) }
    }
}

sealed class SettingScreen(
    route: String,
    label: String,
    icon: ImageVector
) : Screen(
    route = route,
    label = label,
    icon = icon,
) {
    data object WorkoutManage: SettingScreen(
        route = "workoutmanage",
        label = "운동 종목 관리",
        icon = Icons.Default.Build
    )

    data object AppInfo: SettingScreen(
        route = "appinfo",
        label = "앱 정보",
        icon = Icons.Default.Info
    )

    data object Theme: SettingScreen(
        route = "theme",
        label = "테마 설정",
        icon = Icons.Default.Face
    )

    data object AddWorkout: SettingScreen(
        route = "addworkout",
        label = "운동 종목 추가",
        icon = Icons.Default.AddCircle
    )

    companion object {
        val allSettingScreens by lazy { listOf(WorkoutManage, Theme, AppInfo) }
    }
}