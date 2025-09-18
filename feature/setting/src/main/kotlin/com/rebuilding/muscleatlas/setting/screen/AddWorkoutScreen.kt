package com.rebuilding.muscleatlas.setting.screen

import android.R.attr.bottom
import android.R.attr.type
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rebuilding.muscleatlas.design_system.theme.AppColors
import com.rebuilding.muscleatlas.design_system.base.BaseText
import com.rebuilding.muscleatlas.design_system.component.BaseBottomSheet
import com.rebuilding.muscleatlas.design_system.component.BaseTextField
import com.rebuilding.muscleatlas.design_system.component.PrimaryButton
import com.rebuilding.muscleatlas.design_system.component.VerticalDialog
import com.rebuilding.muscleatlas.model.Contraction
import com.rebuilding.muscleatlas.model.MovementData
import com.rebuilding.muscleatlas.model.WorkoutData
import com.rebuilding.muscleatlas.setting.unit.addworkout.RegistedMovementChip
import com.rebuilding.muscleatlas.setting.viewmodel.WorkoutAddSdieEffect
import com.rebuilding.muscleatlas.setting.viewmodel.WorkoutAddViewModel
import com.rebuilding.muscleatlas.ui.extension.hide
import com.rebuilding.muscleatlas.ui.extension.isShown
import com.rebuilding.muscleatlas.ui.extension.rememberDeleteMovementDialogState
import com.rebuilding.muscleatlas.ui.extension.rememberMovementBottomSheetState
import com.rebuilding.muscleatlas.ui.extension.show
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutScreen(
    viewModel: WorkoutAddViewModel = hiltViewModel(),
    workoutId: String,
    onClickBack: () -> Unit = {},
    onClickSave: () -> Unit = {},
) {
    val state by viewModel.state.collectAsState()

    var textNameFieldValue by remember { mutableStateOf<TextFieldState>(TextFieldState("")) }
    var textDescriptionFieldValue by remember { mutableStateOf<TextFieldState>(TextFieldState("")) }
    val uuid by remember { mutableStateOf<UUID>(UUID.randomUUID()) }

    var movementDeleteDialogState = rememberDeleteMovementDialogState<WorkoutAddSdieEffect>()
    val movementAddBottomSheetState = rememberMovementBottomSheetState<WorkoutAddSdieEffect>()

    var profileUri by remember { mutableStateOf<Uri?>(null) }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        profileUri = uri
    }

    BackHandler {
        onClickBack()
    }

    LaunchedEffect(profileUri) {
        Log.d("heesang", "profileUri: $profileUri")
    }

    LaunchedEffect(Unit) {
        if (workoutId.isNotEmpty()) {
            viewModel.initData(workoutId)
        }
    }

    LaunchedEffect(state.workout) {
        textNameFieldValue = TextFieldState(state.workout.title)
        textDescriptionFieldValue = TextFieldState(state.workout.description)
    }

    viewModel.collectSideEffect { sideEffect ->
        when(sideEffect) {
            is WorkoutAddSdieEffect.ShowMovementAddBottomSheet -> {
                movementAddBottomSheetState.show(
                    movement = sideEffect.movement
                )
            }
            is WorkoutAddSdieEffect.ShowMovementDeleteDialog -> {
                movementDeleteDialogState.show(
                    movement = sideEffect.movement
                )
            }
        }
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .padding(bottom = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .imePadding()
                .verticalScroll(rememberScrollState())
        ) {
            BaseTextField(
                textFieldValue = textNameFieldValue.text.toString(),
                onTextChanged = { text ->
                    textNameFieldValue = TextFieldState(text)
                },
                hint = "운동 이름을 입력해주세요.",
                startInputLocation = Alignment.CenterVertically,
            )

            RegistedMovementChip(
                title = "단축성 수축",
                contractionList = state.concentric,
                onClickEdit = { movement ->
                    viewModel.showMovementBottomSheet(movement)
                },
                onClickAdd = { type ->
                    viewModel.showMovementBottomSheet(
                        MovementData(
                            id = "",
                            workoutId = uuid.toString(),
                            contraction = Contraction.Concentric.value,
                            type = type,
                            imgUrl = null,
                            title = "",
                            description = "",
                            currentMills = 0L,
                        )
                    )
                },
                onClickDelete = { movement ->
                    viewModel.showMovementDeleteDialog(movement)
                }
            )

            RegistedMovementChip(
                title = "신장성 수축",
                contractionList = state.eccentric,
                onClickEdit = { movement ->
                    viewModel.showMovementBottomSheet(movement)
                },
                onClickAdd = { type ->
                    viewModel.showMovementBottomSheet(
                        MovementData(
                            id = "",
                            workoutId = uuid.toString(),
                            contraction = Contraction.Eccentric.value,
                            type = type,
                            imgUrl = null,
                            title = "",
                            description = "",
                            currentMills = 0L,
                        )
                    )
                },
                onClickDelete = { movement ->
                    viewModel.showMovementDeleteDialog(movement)
                }
            )

            BaseText(
                text = "설명",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Start
            )

            Spacer(Modifier.height(4.dp))

            BaseTextField(
                textFieldValue = textDescriptionFieldValue.text.toString(),
                onTextChanged = { text ->
                    textDescriptionFieldValue = TextFieldState(text)
                },
                hint = "운동 설명을 입력해주세요.",
                startInputLocation = Alignment.Top,
                modifier = Modifier.height(400.dp)
            )

            Spacer(Modifier
                .fillMaxWidth()
                .height(48.dp))
        }

        Box (
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            PrimaryButton(
                text = "저장",
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (textNameFieldValue.text.length > 0 && textDescriptionFieldValue.text.length > 0) {
                        viewModel.updateMovementsWithWorkout(
                            workoutData = WorkoutData(
                                id = if (state.workout.id.isNotEmpty()) state.workout.id else uuid.toString(),
                                imgUrl = null,
                                title = textNameFieldValue.text.toString(),
                                description = textDescriptionFieldValue.text.toString(),
                                currentMills = if (state.workout.currentMills > 0L) state.workout.currentMills else System.currentTimeMillis(),
                            ),
                        )

                        onClickSave()
                    }
                }
            )
        }
    }

    if (movementAddBottomSheetState.isShown) {
        BaseBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
                confirmValueChange = { state ->
                    state != SheetValue.PartiallyExpanded
                },
            ),
            sheetGesturesEnabled = false,
            onDismissRequest = {
                movementAddBottomSheetState.hide()
            },
        ) {
            MovementBottomSheetScreen(
                state = movementAddBottomSheetState,
                uri = profileUri,
                onSelectedImage = {
                    galleryLauncher.launch("image/*")
                },
                onClickedClose = {
                    movementAddBottomSheetState.hide()
                },
                onSaveMovement = { movement ->
                    viewModel.updateMovementUI(movement)
                    movementAddBottomSheetState.hide()
                }
            )
        }
    }

    if (movementDeleteDialogState.isShown) {
        VerticalDialog(
            title = "운동을 삭제하시겠습니까?",
            description = "삭제된 운동은 복구할 수 없습니다.",
            leftButton = {
                movementDeleteDialogState.hide()
            },
            rightButton = {
                viewModel.deleteMovementUI(movementDeleteDialogState.value.movement)
                movementDeleteDialogState.hide()
            },
            onDismiss = {
                movementDeleteDialogState.hide()
            }
        )
    }
}