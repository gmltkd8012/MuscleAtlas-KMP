package com.rebuilding.muscleatlas.main.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rebuilding.muscleatlas.client.screen.ClientScreen
import com.rebuilding.muscleatlas.design_system.AppColors
import com.rebuilding.muscleatlas.main.component.BottomNavigationBar
import com.rebuilding.muscleatlas.main.component.MainHeaderBar
import com.rebuilding.muscleatlas.model.Screen
import com.rebuilding.muscleatlas.setting.SettingScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var headerTitle by remember { mutableStateOf<String>("") }

    Scaffold(
        modifier = Modifier.background(AppColors.primary),
        topBar = {
            MainHeaderBar(
                title = headerTitle,
                isNeedAdd = true,
                onClickAdd = {

                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController, Screen.allScreens)
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = Screen.Client.route
        ) {
            composable(Screen.Client.route) {
                headerTitle = Screen.Client.label
                ClientScreen()
            }
            composable(Screen.Setting.route) {
                headerTitle = Screen.Setting.label
                SettingScreen()
            }
        }
    }
}

