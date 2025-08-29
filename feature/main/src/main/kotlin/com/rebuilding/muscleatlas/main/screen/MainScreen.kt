package com.rebuilding.muscleatlas.main.screen

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rebuilding.muscleatlas.client.ClientProfileActivity
import com.rebuilding.muscleatlas.client.screen.ClientAddScreen
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.component.BaseBottomSheet
import com.rebuilding.muscleatlas.design_system.theme.MuscleAtlasTheme
import com.rebuilding.muscleatlas.main.component.BottomNavigationBar
import com.rebuilding.muscleatlas.main.component.MainHeaderBar
import com.rebuilding.muscleatlas.main.viewmodel.MainViewModel
import com.rebuilding.muscleatlas.model.AppTheme
import com.rebuilding.muscleatlas.model.Screen
import com.rebuilding.muscleatlas.setting.SettingActivity
import com.rebuilding.muscleatlas.ui.extension.startActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    isDarkTheme: Boolean,
    viewModel: MainViewModel = hiltViewModel<MainViewModel>()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    val navController = rememberNavController()
    var headerTitle by remember { mutableStateOf<String>("") }

    var showClientAddBottomSheet by remember { mutableStateOf(false) }


    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.primary),
        topBar = {
            MainHeaderBar(
                title = headerTitle,
                isNeedAdd = true,
                onClickAdd = {
                    showClientAddBottomSheet = true
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
            startDestination = Screen.Client.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            composable(Screen.Client.route) {
                headerTitle = Screen.Client.label
                ClientMainScreen(
                    onClickProfile = {
                        context.startActivity<ClientProfileActivity>()
                    }
                )
            }
            composable(Screen.Setting.route) {
                headerTitle = Screen.Setting.label
                SettingMainScreen(
                    onClickMenu = { route ->
                        context.startActivity<SettingActivity>(
                            argument = arrayOf(
                                "destination" to route,
                                "isdarktheme" to AppTheme.Dark.id
                            )
                        )
                    }
                )
            }
        }
    }

    if (showClientAddBottomSheet) {
        BaseBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
                confirmValueChange = { state ->
                    state != SheetValue.PartiallyExpanded
                },
            ),
            onDismissRequest = {
                showClientAddBottomSheet = false

            },
        ) {
            ClientAddScreen(
                onDismissRequest = {
                    showClientAddBottomSheet = false
                }
            )
        }
    }

}

