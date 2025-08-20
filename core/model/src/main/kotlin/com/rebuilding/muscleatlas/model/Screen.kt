package com.rebuilding.muscleatlas.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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