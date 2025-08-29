package com.rebuilding.muscleatlas.client.screen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
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
    intent: Intent,
    onClickBack: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    var isProfileScreen by remember { mutableStateOf(true) }
    var movementDetailBottomSheet by remember { mutableStateOf(false) }

    MuscleAtlasTheme() {
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
                    clientName = "테스터",
                    memo = "테스트 체육관",
                    workoutName = "벤치 프레스",
                    isColumn = isProfileScreen
                )

                if (isProfileScreen) {
                    Spacer(Modifier.height(16.dp))

                    ClientWorkoutChip(
                        workoutList = listOf(
                            "벤치프레스",
                            "스쿼트",
                            "데드리프트",
                            "밀리터리프레스",
                            "인클라인벤치프레스",
                            "덤벨숄더프레스",
                            "런지",
                            "레그컬",
                            "트라이셉스익스텐션",
                            "바벨컬",
                        ),
                        onClickedWorkout = {
                            isProfileScreen = false
                        }
                    )
                } else {
                    Spacer(Modifier.height(32.dp))

                    MovemenetChip(
                        title = Movement.JoinMovement.title,
                        icon = Icons.Default.Warning,
                        movemenetList = listOf(
                            "동작 1",
                            "동작 2",
                            "동작 3",
                            "동작 4",
                            "동작 5",
                            "동작 6",
                            "동작 7",
                        ),
                        onClick = {
                            movementDetailBottomSheet = true
                        }
                    )

                    Spacer(Modifier.height(16.dp))

                    MovemenetChip(
                        title = Movement.StabilizationMechanism.title,
                        icon = Icons.Default.Warning,
                        movemenetList = listOf(
                            "동작 1",
                            "동작 2",
                            "동작 3",
                            "동작 4",
                            "동작 5",
                            "동작 6",
                            "동작 7",
                        ),
                        onClick = {
                            movementDetailBottomSheet = true
                        }
                    )

                    Spacer(Modifier.height(16.dp))

                    MovemenetChip(
                        title = Movement.NeuromuscularRelation.title,
                        icon = Icons.Default.Warning,
                        movemenetList = listOf(
                            "동작 1",
                            "동작 2",
                            "동작 3",
                            "동작 4",
                            "동작 5",
                            "동작 6",
                            "동작 7",
                        ),
                        onClick = {
                            movementDetailBottomSheet = true
                        }
                    )
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