package com.rebuilding.muscleatlas.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rebuilding.muscleatlas.client.ClientScreen
import com.rebuilding.muscleatlas.main.component.BottomNavItem
import com.rebuilding.muscleatlas.main.component.BottomNavigationBar
import com.rebuilding.muscleatlas.setting.SettingScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val menuItems = listOf(
        BottomNavItem("client", "회원 관리", Icons.Default.AccountCircle),
        BottomNavItem("setting", "설정", Icons.Default.Settings)
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, menuItems)
        }
    ) { innerPadding ->

        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = "client"
        ) {
            composable("client") {
                ClientScreen()
            }
            composable("setting") {
                SettingScreen()
            }
        }
    }
}

