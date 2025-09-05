package com.rebuilding.muscleatlas.client.screen

import android.R.attr.onClick
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rebuilding.muscleatlas.client.component.ClientProfileTopBar
import com.rebuilding.muscleatlas.client.unit.ClientProfileBox
import com.rebuilding.muscleatlas.client.unit.ClientWorkoutChip
import com.rebuilding.muscleatlas.client.unit.MovemenetChip
import com.rebuilding.muscleatlas.client.viewmodel.ClientViewModel
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.component.BaseBottomSheet
import com.rebuilding.muscleatlas.design_system.theme.MuscleAtlasTheme
import com.rebuilding.muscleatlas.model.Movement

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientProfileScreen(
    viewModel: ClientViewModel = hiltViewModel<ClientViewModel>(),
    clientId: String = "",
    isDarkTheme: Boolean,
    onClickBack: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    var isProfileScreen by remember { mutableStateOf(true) }
    var movementDetailBottomSheet by remember { mutableStateOf(false) }

    BackHandler { onClickBack }

    LaunchedEffect(Unit) {
        if (clientId.isNotEmpty()) {
            viewModel.getClientWithWorkouts(clientId)
        }
    }

    MuscleAtlasTheme(
        darkTheme = isDarkTheme,
    ) {
        Scaffold(
            modifier = Modifier.background(MaterialTheme.colorScheme.primary),
            topBar = {
                ClientProfileTopBar(
                    title = if (isProfileScreen) "회원 정보" else "운동 정보",
                    onClickBack = {
                        if (isProfileScreen) onClickBack()
                        else isProfileScreen = true

                    }
                )
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ClientProfileBox(
                    clientName = state.client.name,
                    memo = state.client.memo,
                    workoutName = state.selectedWorkout.title,
                    isColumn = isProfileScreen
                )

                if (isProfileScreen) {
                    Spacer(Modifier.height(16.dp))

                    ClientWorkoutChip(
                        workoutList = state.workoutList,
                        onClickedWorkout = { selectedWorkout ->
                            viewModel.selectedWorkout(selectedWorkout)
                            isProfileScreen = false
                        }
                    )
                } else {
                    Spacer(Modifier.height(32.dp))

                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        MovemenetChip(
                            title = Movement.JoinMovement.title,
                            icon = Icons.Default.Warning,
                            movemenetList = state.joinMovementList,
                            onClick = {
                                movementDetailBottomSheet = true
                            }
                        )

                        Spacer(Modifier.height(16.dp))

                        MovemenetChip(
                            title = Movement.StabilizationMechanism.title,
                            icon = Icons.Default.Warning,
                            movemenetList = state.stabilizationMechanismList,
                            onClick = {
                                movementDetailBottomSheet = true
                            }
                        )

                        Spacer(Modifier.height(16.dp))

                        MovemenetChip(
                            title = Movement.NeuromuscularRelation.title,
                            icon = Icons.Default.Warning,
                            movemenetList = state.neuromuscularRelationList,
                            onClick = {
                                movementDetailBottomSheet = true
                            }
                        )

                        Spacer(Modifier.height(100.dp))
                    }
                }
            }

            if (movementDetailBottomSheet) {
                BaseBottomSheet(
                    modifier = Modifier.fillMaxWidth(),
                    sheetState = rememberModalBottomSheetState(
                        skipPartiallyExpanded = true,
                        confirmValueChange = { state ->
                            state != SheetValue.PartiallyExpanded
                        },
                    ),
                    isDragHandle = false,
                    onDismissRequest = {
                        movementDetailBottomSheet = false
                    },
                ) {
                    MovementDetailScreen(
                        title = "ㅋㅋ",
                        description = "세부 동작에 대한 설명",
                        imgUrl = null,
                        onClickBack = {
                            movementDetailBottomSheet = false
                        },
                    )
                }
            }

        }
    }
}